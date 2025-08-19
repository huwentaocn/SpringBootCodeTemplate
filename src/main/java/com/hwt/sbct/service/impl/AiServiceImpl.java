package com.hwt.sbct.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.nls.client.protocol.InputFormatEnum;
import com.alibaba.nls.client.protocol.NlsClient;
import com.alibaba.nls.client.protocol.SampleRateEnum;
import com.alibaba.nls.client.protocol.asr.SpeechTranscriber;
import com.alibaba.nls.client.protocol.asr.SpeechTranscriberListener;
import com.alibaba.nls.client.protocol.asr.SpeechTranscriberResponse;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.tingwu20230930.models.*;
import com.aliyun.sdk.service.tingwu20230930.*;
import com.google.gson.Gson;
import com.hwt.sbct.constant.ManufacturerEnum;
import com.hwt.sbct.design.ai.ManufacturerFactory;
import com.hwt.sbct.exception.GlobalException;
import com.hwt.sbct.pojo.req.RealTimeRecordingTaskOperateReq;
import com.hwt.sbct.pojo.resp.RealTimeRecordingTaskOperateResp;
import com.hwt.sbct.result.ResultCodeEnum;
import com.hwt.sbct.service.AiService;
import darabonba.core.client.ClientOverrideConfiguration;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//import javax.net.ssl.KeyManager;
//import javax.net.ssl.X509TrustManager;
import java.io.FileInputStream;
import java.util.*;
import java.util.concurrent.CompletableFuture;


/**
 * @Description 实时ai业务实现
 * @Date 2025/6/5 17:33 星期四
 * @Author Hu Wentao
 */

@Slf4j
@Service
public class AiServiceImpl implements AiService {

    @Value("${aliyun.tongyitingwu.access-key-id}")
    private static String ALI_ACCESS_KEY_ID;

    @Value("${aliyun.tongyitingwu.access-key-secret}")
    private static String ALI_ACCESS_KEY_SECRET;

    @Value("${aliyun.tongyitingwu.access-key-id}")
    private static String ALI_TONGYITINGWU_APP_KEY;


    @Override
    public RealTimeRecordingTaskOperateResp createRealTimeRecordingTask(RealTimeRecordingTaskOperateReq req) {

        //TODO 这里先写死的阿里，后面可以通过业务查询出应该用哪个厂商的
        return ManufacturerFactory.getInstance(ManufacturerEnum.ALI_CLOUD_TONGYITINGWU.code).createRealTimeRecordingTask(req);
    }

    @Override
    public RealTimeRecordingTaskOperateResp stopRealTimeRecordingTask(RealTimeRecordingTaskOperateReq req) {


        //TODO 这里先写死的阿里，后面可以通过业务查询出应该用哪个厂商的
        return ManufacturerFactory.getInstance(ManufacturerEnum.ALI_CLOUD_TONGYITINGWU.code).stopRealTimeRecordingTask(req);
    }

    @Override
    public RealTimeRecordingTaskOperateResp getRealTimeRecordingTaskInfo(RealTimeRecordingTaskOperateReq req) {
        //TODO 这里先写死的阿里，后面可以通过业务查询出应该用哪个厂商的
        return ManufacturerFactory.getInstance(ManufacturerEnum.ALI_CLOUD_TONGYITINGWU.code).getRealTimeRecordingTaskInfo(req);
    }

    @SneakyThrows
    @Override
    public void testRealtimeTrans(String meetingJoinUrl) {
        // 此处url来自于用户通过OpenAPI创建会议时返回的推流url， url地址一般是："wss://tingwu-realtime-cn-beijing.aliyuncs.com/api/ws/v1?mc={xxxxxxzzzzyyy}"

        SpeechTranscriber speechTranscriber = new SpeechTranscriber(new NlsClient("default"), "default" ,createListener(), meetingJoinUrl);
//        /**--------------------*/
        //speechTranscriber.setAppKey(appKey);
        // 输入音频编码方式
        speechTranscriber.setFormat(InputFormatEnum.PCM);
        // 输入音频采样率
        speechTranscriber.setSampleRate(SampleRateEnum.SAMPLE_RATE_16K);
        // 是否返回中间识别结果
        speechTranscriber.setEnableIntermediateResult(true);
        // 是否生成并返回标点符号
        speechTranscriber.setEnablePunctuation(true);
        // 是否将返回结果规整化,比如将一百返回为100
        speechTranscriber.setEnableITN(false);
        speechTranscriber.start();

        // 使用本地文件模拟 真实场景下音频流实时采集
//        String localAudioFile = "src/main/resources/vad_example.wav";
        String localAudioFile = "src/main/resources/xc.wav";
        byte[] buffer = new byte[3200];
        FileInputStream fis = new FileInputStream(localAudioFile);
        int len;
        while ((len = fis.read(buffer)) > 0) {

            log.info("二进制数据：{}", JSONObject.toJSONString(Arrays.copyOf(buffer, len)));
            // TODO 模拟实时发送的音频数据帧
            speechTranscriber.send(Arrays.copyOf(buffer, len));
            // TODO 模拟音频采集间隔
            Thread.sleep(100L);
        }

        // 音频流结束后，发送stop结束实时转写处理。
        // TODO 注意： 此时该会议并没有结束，会议的结束可以参考StopRealtimeMeetingTaskTest处理。
        speechTranscriber.stop();
        speechTranscriber.close();
    }

    public SpeechTranscriberListener createListener() {
        return new SpeechTranscriberListener() {
            @Override
            public void onMessage(String message) {
                log.info("onMessage " + message);
                if (message == null || message.trim().length() == 0) {
                    return;
                }
                SpeechTranscriberResponse response = JSON.parseObject(message, SpeechTranscriberResponse.class);
                if("ResultTranslated".equals(response.getName())) {
                    // 翻译事件输出，您可以在此处进行相关处理
                    log.info("--- ResultTranslated ---" + JSON.toJSONString(response, SerializerFeature.PrettyFormat));
                } else {
                    // 原语音识别事件输出，交由父类负责回调
                    super.onMessage(message);
                }
            }

            @Override
            public void onTranscriberStart(SpeechTranscriberResponse response) {
                // task_idf非常重要，但需要说明的是，该task_id是在音频流实时推送和识别过程中的标识，而非会议级别的TaskId
                log.info("task_id: " + response.getTaskId() + ", name: " + response.getName() + ", status: " + response.getStatus());
            }

            @Override
            public void onSentenceBegin(SpeechTranscriberResponse response) {
                log.info("received onSentenceBegin: " + JSON.toJSONString(response));
            }

            @Override
            public void onSentenceEnd(SpeechTranscriberResponse response) {
                //识别出一句话。服务端会智能断句，当识别到一句话结束时会返回此消息。
                log.info("received onSentenceEnd: " + JSON.toJSONString(response));
                log.info("task_id: " + response.getTaskId() +
                        ", name: " + response.getName() +
                        // 状态码“20000000”表示正常识别。
                        ", status: " + response.getStatus() +
                        // 句子编号，从1开始递增。
                        ", index: " + response.getTransSentenceIndex() +
                        // 当前的识别结果。
                        ", result: " + response.getTransSentenceText() +
                        // 当前的词模式识别结果。
                        ", words: " + response.getWords() +
                        // 开始时间
                        ", begin_time: " + response.getSentenceBeginTime() +
                        // 当前已处理的音频时长，单位为毫秒。
                        ", time: " + response.getTransSentenceTime());
                // 当前的识别结果(固定的，不再变化的识别结果)
                String text = response.getTransSentenceText();
                // 当前的识别结果(不同于response.getTransSentenceText()， 此处的识别结果可能会出现变化)
                SpeechTranscriberResponse.StashResult stashResult = response.getStashResult();
                // 将上面两段识别结果拼接起来
                String stashText = stashResult == null ? "" : stashResult.getText();
                log.info("[onSentenceEnd] text = " + text + " | stashText = " + stashText);
            }

            @Override
            public void onTranscriptionResultChange(SpeechTranscriberResponse response) {
                // 识别出中间结果。仅当OutputLevel=2时，才会返回该消息。
                log.info("received onTranscriptionResultChange: " + JSON.toJSONString(response));
                log.info("task_id: " + response.getTaskId() +
                        ", name: " + response.getName() +
                        // 状态码“20000000”表示正常识别。
                        ", status: " + response.getStatus() +
                        // 句子编号，从1开始递增。
                        ", index: " + response.getTransSentenceIndex() +
                        // 当前的识别结果。
                        ", result: " + response.getTransSentenceText() +
                        // 当前的词模式识别结果。
                        ", words: " + response.getWords() +
                        // 当前已处理的音频时长，单位为毫秒。
                        ", time: " + response.getTransSentenceTime());
            }

            @Override
            public void onTranscriptionComplete(SpeechTranscriberResponse response) {
                // 识别结束，当调用speechTranscriber.stop()之后会收到该事件
                log.info("received onTranscriptionComplete: " + JSON.toJSONString(response));
            }

            @Override
            public void onFail(SpeechTranscriberResponse response) {
                // 实时识别出错，请关注错误码，请记录此task_id以便排查
                log.info("received onFail: " + JSON.toJSONString(response));
            }
        };
    }

    public static void main(String[] args) throws Exception {
        AsyncClient client = null;
        try {

            // HttpClient Configuration
        /*HttpClient httpClient = new ApacheAsyncHttpClientBuilder()
                .connectionTimeout(Duration.ofSeconds(10)) // Set the connection timeout time, the default is 10 seconds
                .responseTimeout(Duration.ofSeconds(10)) // Set the response timeout time, the default is 20 seconds
                .maxConnections(128) // Set the connection pool size
                .maxIdleTimeOut(Duration.ofSeconds(50)) // Set the connection pool timeout, the default is 30 seconds
                // Configure the proxy
                .proxy(new ProxyOptions(ProxyOptions.Type.HTTP, new InetSocketAddress("<your-proxy-hostname>", 9001))
                        .setCredentials("<your-proxy-username>", "<your-proxy-password>"))
                // If it is an https connection, you need to configure the certificate, or ignore the certificate(.ignoreSSL(true))
                .x509TrustManagers(new X509TrustManager[]{})
                .keyManagers(new KeyManager[]{})
                .ignoreSSL(false)
                .build();*/

            // Configure Credentials authentication information, including ak, secret, token
            StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                    // Please ensure that the environment variables ALIBABA_CLOUD_ACCESS_KEY_ID and ALIBABA_CLOUD_ACCESS_KEY_SECRET are set.
                    .accessKeyId(ALI_ACCESS_KEY_ID)
                    .accessKeySecret(ALI_ACCESS_KEY_SECRET)
                    //.securityToken(System.getenv("ALIBABA_CLOUD_SECURITY_TOKEN")) // use STS token
                    .build());

            // Configure the Client
            client = AsyncClient.builder()
                    .region("cn-beijing") // Region ID
                    //.httpClient(httpClient) // Use the configured HttpClient, otherwise use the default HttpClient (Apache HttpClient)
                    .credentialsProvider(provider)
                    //.serviceConfiguration(Configuration.create()) // Service-level configuration
                    // Client-level configuration rewrite, can set Endpoint, Http request parameters, etc.
                    .overrideConfiguration(
                            ClientOverrideConfiguration.create()
                                    // Endpoint 请参考 https://api.aliyun.com/product/tingwu
                                    .setEndpointOverride("tingwu.cn-beijing.aliyuncs.com")
                            //.setConnectTimeout(Duration.ofSeconds(30))
                    )
                    .build();

            // Parameter settings for API request
            CreateTaskRequest.Translation parametersTranslation = CreateTaskRequest.Translation.builder()
                    .targetLanguages(Arrays.asList(
                            "cn"
                    ))
                    .outputLevel(2)
                    .additionalStreamOutputLevel(2)
                    .build();
            CreateTaskRequest.Diarization parametersTranscriptionDiarization = CreateTaskRequest.Diarization.builder()
                    .speakerCount(0)
                    .build();
            CreateTaskRequest.Transcription parametersTranscription = CreateTaskRequest.Transcription.builder()
                    .diarization(parametersTranscriptionDiarization)
                    .outputLevel(2)
                    .additionalStreamOutputLevel(2)
                    .build();
            CreateTaskRequest.Parameters parameters = CreateTaskRequest.Parameters.builder()
                    .transcription(parametersTranscription)
                    .translation(parametersTranslation)
                    .translationEnabled(true)
                    .autoChaptersEnabled(true)
                    .meetingAssistanceEnabled(true)
                    .pptExtractionEnabled(true)
                    .textPolishEnabled(true)
                    .customPromptEnabled(false)
                    .build();
            CreateTaskRequest.Input input = CreateTaskRequest.Input.builder()
                    .sourceLanguage("multilingual")
                    .taskKey("task" + System.currentTimeMillis())
                    .format("pcm")
                    .sampleRate(16000)
                    .progressiveCallbacksEnabled(true)
                    .build();
            CreateTaskRequest createTaskRequest = CreateTaskRequest.builder()
                    .appKey(ALI_TONGYITINGWU_APP_KEY)
                    .type("realtime")/*offline：表示离线任务，比如离线转写;realtime：表示实时任务，比如创建实时记录*/
//                .operation("start") /*start：表示任务的创建，也是默认值，通常情况下无须显示设置;stop：停止实时会议任务，对应的是创建实时会议，在会议结束后设置为 stop 并触发调用；实时会议场景使用；需要注意：在结束实时记录时，务必设置此参数，且设置为 stop。*/
                    .input(input)
                    .parameters(parameters)
                    // Request-level configuration rewrite, can set Http request parameters, etc.
                    // .requestConfiguration(RequestConfiguration.create().setHttpHeaders(new HttpHeaders()))
                    .build();

            // Asynchronously get the return value of the API request
            CompletableFuture<CreateTaskResponse> response = client.createTask(createTaskRequest);
            // Synchronously get the return value of the API request
            CreateTaskResponse resp = response.get();
            log.info("CreateTaskResponse : {}", new Gson().toJson(resp));
            // Asynchronous processing of return values
        /*response.thenAccept(resp -> {
            System.out.println(new Gson().toJson(resp));
        }).exceptionally(throwable -> { // Handling exceptions
            System.out.println(throwable.getMessage());
            return null;
        });*/

            CreateTaskResponseBody.Data data = checkAliCreateTaskResponse(resp);
            log.info("CreateTaskResponseBody.Data : {}", JSONObject.toJSONString(data));


        } catch (Exception e) {
            log.error("调用阿里通义听悟创建实时任务失败: e ==> {}", e.getMessage());
            throw new GlobalException(ResultCodeEnum.ALI_CLOUD_CALL_FAIL, "调用阿里通义听悟创建实时任务失败:" + e.getMessage());
        } finally {
            // Finally, close the client
            if (client != null) {
                client.close();
            }
        }
    }

    private static CreateTaskResponseBody.Data checkAliCreateTaskResponse(CreateTaskResponse response) {
        if (Objects.isNull(response) || Objects.isNull(response.getBody())) {
            throw new GlobalException(ResultCodeEnum.ALI_CLOUD_CALL_FAIL, "调用阿里通义听悟创建实时任务失败");
        }

        CreateTaskResponseBody responseBody = response.getBody();
        if (!"0".equals(responseBody.getCode())) {
            throw new GlobalException(ResultCodeEnum.ALI_CLOUD_CALL_FAIL, "调用阿里通义听悟创建实时任务失败：" + responseBody.getMessage());
        }

        return responseBody.getData();
    }


    public static void main1(String[] args) throws Exception {
        AsyncClient client = null;
        try {

            // HttpClient Configuration
        /*HttpClient httpClient = new ApacheAsyncHttpClientBuilder()
                .connectionTimeout(Duration.ofSeconds(10)) // Set the connection timeout time, the default is 10 seconds
                .responseTimeout(Duration.ofSeconds(10)) // Set the response timeout time, the default is 20 seconds
                .maxConnections(128) // Set the connection pool size
                .maxIdleTimeOut(Duration.ofSeconds(50)) // Set the connection pool timeout, the default is 30 seconds
                // Configure the proxy
                .proxy(new ProxyOptions(ProxyOptions.Type.HTTP, new InetSocketAddress("<your-proxy-hostname>", 9001))
                        .setCredentials("<your-proxy-username>", "<your-proxy-password>"))
                // If it is an https connection, you need to configure the certificate, or ignore the certificate(.ignoreSSL(true))
                .x509TrustManagers(new X509TrustManager[]{})
                .keyManagers(new KeyManager[]{})
                .ignoreSSL(false)
                .build();*/

            // Configure Credentials authentication information, including ak, secret, token
            StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                    // Please ensure that the environment variables ALIBABA_CLOUD_ACCESS_KEY_ID and ALIBABA_CLOUD_ACCESS_KEY_SECRET are set.
                    .accessKeyId(ALI_ACCESS_KEY_ID)
                    .accessKeySecret(ALI_ACCESS_KEY_SECRET)
                    //.securityToken(System.getenv("ALIBABA_CLOUD_SECURITY_TOKEN")) // use STS token
                    .build());

            // Configure the Client
            client = AsyncClient.builder()
                    .region("cn-beijing") // Region ID
                    //.httpClient(httpClient) // Use the configured HttpClient, otherwise use the default HttpClient (Apache HttpClient)
                    .credentialsProvider(provider)
                    //.serviceConfiguration(Configuration.create()) // Service-level configuration
                    // Client-level configuration rewrite, can set Endpoint, Http request parameters, etc.
                    .overrideConfiguration(
                            ClientOverrideConfiguration.create()
                                    // Endpoint 请参考 https://api.aliyun.com/product/tingwu
                                    .setEndpointOverride("tingwu.cn-beijing.aliyuncs.com")
                            //.setConnectTimeout(Duration.ofSeconds(30))
                    )
                    .build();

            // Parameter settings for API request
            CreateTaskRequest.Input input = CreateTaskRequest.Input.builder()
                    .taskId("7e9cba274fe44e25afde9573957220dc")
                    .build();
            CreateTaskRequest createTaskRequest = CreateTaskRequest.builder()
                    .appKey(ALI_TONGYITINGWU_APP_KEY)
                    .type("realtime")/*offline：表示离线任务，比如离线转写;realtime：表示实时任务，比如创建实时记录*/
                    .operation("stop") /*start：表示任务的创建，也是默认值，通常情况下无须显示设置;stop：停止实时会议任务，对应的是创建实时会议，在会议结束后设置为 stop 并触发调用；实时会议场景使用；需要注意：在结束实时记录时，务必设置此参数，且设置为 stop。*/
                    .input(input)
                    // Request-level configuration rewrite, can set Http request parameters, etc.
                    // .requestConfiguration(RequestConfiguration.create().setHttpHeaders(new HttpHeaders()))
                    .build();

            // Asynchronously get the return value of the API request
            CompletableFuture<CreateTaskResponse> response = client.createTask(createTaskRequest);
            // Synchronously get the return value of the API request
            CreateTaskResponse resp = response.get();
            System.out.println(new Gson().toJson(resp));
            // Asynchronous processing of return values
        /*response.thenAccept(resp -> {
            System.out.println(new Gson().toJson(resp));
        }).exceptionally(throwable -> { // Handling exceptions
            System.out.println(throwable.getMessage());
            return null;
        });*/

            log.info("StopTaskResponse : {}", new Gson().toJson(resp));

            CreateTaskResponseBody.Data data = checkAliCreateTaskResponse(resp);
            log.info("CreateTaskResponseBody.Data : {}", JSONObject.toJSONString(data));


        } catch (Exception e) {
            log.error("调用阿里通义听悟创建实时任务失败: e ==> {}", e.getMessage());
            throw new GlobalException(ResultCodeEnum.ALI_CLOUD_CALL_FAIL, "调用阿里通义听悟创建实时任务失败:" + e.getMessage());
        } finally {
            // Finally, close the client
            if (client != null) {
                client.close();
            }
        }
    }

}
