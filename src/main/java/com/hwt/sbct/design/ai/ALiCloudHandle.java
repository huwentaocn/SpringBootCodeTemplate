package com.hwt.sbct.design.ai;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.tingwu20230930.AsyncClient;
import com.aliyun.sdk.service.tingwu20230930.models.*;
import com.google.gson.Gson;
import com.hwt.sbct.constant.ManufacturerEnum;
import com.hwt.sbct.exception.GlobalException;
import com.hwt.sbct.pojo.req.RealTimeRecordingTaskOperateReq;
import com.hwt.sbct.pojo.resp.RealTimeRecordingTaskOperateResp;
import com.hwt.sbct.pojo.resp.RealTimeRecordingTaskResultResp;
import com.hwt.sbct.result.ResultCodeEnum;
import darabonba.core.client.ClientOverrideConfiguration;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @Description 阿里处理器
 * @Date 2025/6/6 14:30 星期五
 * @Author Hu Wentao
 */
@Slf4j
@Service
public class ALiCloudHandle extends ManufacturerTemplate {

    @Value("${aliyun.tongyitingwu.access-key-id}")
    private String ALI_ACCESS_KEY_ID;

    @Value("${aliyun.tongyitingwu.access-key-secret}")
    private String ALI_ACCESS_KEY_SECRET;

    @Value("${aliyun.tongyitingwu.app-key}")
    private String ALI_TONGYITINGWU_APP_KEY;

    @Override
    public void afterPropertiesSet() throws Exception {
        ManufacturerFactory.register(ManufacturerEnum.ALI_CLOUD_TONGYITINGWU, this);
    }

    @SneakyThrows
    @Override
    public RealTimeRecordingTaskOperateResp createRealTimeRecordingTask(RealTimeRecordingTaskOperateReq req) {
        AsyncClient client = null;
        try {
            String taskKey = req.getTaskKey();

            //获取client
            client = getAsyncClient();

            // Parameter settings for API request
            //身份识别的内容列表，包含身份名称和描述。
            CreateTaskRequest.IdentityContents parametersIdentityRecognitionIdentityContents1 = CreateTaskRequest.IdentityContents.builder()
                    .name("学生") //身份名称
                    .description("听讲，回答问题") //身份描述
                    .build();
            CreateTaskRequest.IdentityContents parametersIdentityRecognitionIdentityContents0 = CreateTaskRequest.IdentityContents.builder()
                    .name("老师")
                    .description("讲课老师，提问")
                    .build();
            CreateTaskRequest.IdentityRecognition parametersIdentityRecognition = CreateTaskRequest.IdentityRecognition.builder()
                    .sceneIntroduction("教育场景、会议场景") //身份识别的场景描述
                    .identityContents(java.util.Arrays.asList(
                            parametersIdentityRecognitionIdentityContents0,
                            parametersIdentityRecognitionIdentityContents1
                    ))
                    .build();
            //对话内容提取参数对象。
            CreateTaskRequest.ExtractionContents parametersContentExtractionExtractionContents1 = CreateTaskRequest.ExtractionContents.builder()
                    .title("学生上课") //对话内容提取的提取维度名称
                    .identity("学生") //身份
                    .content("学生听讲，回答问题") //对话内容提取的维度定义
                    .build();
            CreateTaskRequest.ExtractionContents parametersContentExtractionExtractionContents0 = CreateTaskRequest.ExtractionContents.builder()
                    .title("老师上课")
                    .identity("老师")
                    .content("老师讲课，提问")
                    .build();
            CreateTaskRequest.ContentExtraction parametersContentExtraction = CreateTaskRequest.ContentExtraction.builder()
                    .sceneIntroduction("教育课堂") //对话内容提取的场景描述
                    .extractionContents(java.util.Arrays.asList(
                            parametersContentExtractionExtractionContents0,
                            parametersContentExtractionExtractionContents1
                    ))
                    .build();
            /**
             * 摘要功能控制参数
             * Paragraph：全文摘要
             * Conversational：发言人总结摘要
             * QuestionsAnswering：问答回顾摘要
             */
            CreateTaskRequest.Summarization parametersSummarization = CreateTaskRequest.Summarization.builder()
                    .types(java.util.Arrays.asList(
                            "Conversational",
                            "Paragraph",
                            "QuestionsAnswering"
                    ))
                    .build();
            /**
             * 设置智能纪要功能的算法类型。可设置以下取值：
             * Actions：待办事项
             * KeyInformation：关键信息处理，含关键词、重点内容等
             */
            CreateTaskRequest.MeetingAssistance parametersMeetingAssistance = CreateTaskRequest.MeetingAssistance.builder()
                    .types(java.util.Arrays.asList(
                            "KeyInformation"
                    ))
                    .build();

            /**
             * 翻译功能开启时设置的目标语言。 支持以下取值：
             * cn：中文
             * en：英文
             * ja：日文
             */
            CreateTaskRequest.Translation parametersTranslation = CreateTaskRequest.Translation.builder()
                    .targetLanguages(java.util.Arrays.asList(
                            "cn",
                            "en",
                            "ja"
                    ))
                    .outputLevel(2) //设置语音识别结果返回等级。默认值是 1: 1：识别出完整句子时返回识别结果；2：识别出中间结果及完整句子时返回识别结果
                    .additionalStreamOutputLevel(2) //置实时记录场景下活跃说话人对应的语音识别结果返回等级。 1：识别出完整句子时返回识别结果； 2：识别出中间结果及完整句子时返回识别结果；
                    .build();

            /**
             * 设置说话人分离参数。
             * 不设置：不使用说话人角色区分。
             * 0：说话人角色区分结果为不定人数。
             * 2：说话人角色区分结果为 2 人。
             */
            CreateTaskRequest.Diarization parametersTranscriptionDiarization = CreateTaskRequest.Diarization.builder()
                    .speakerCount(0)
                    .build();

            //语音转写控制参数
            CreateTaskRequest.Transcription parametersTranscription = CreateTaskRequest.Transcription.builder()
                    .audioEventDetectionEnabled(true) //是否在语音转写过程中开启声音事件检测功能，用以判断音频中是否存在比如 music 等事件
                    .diarizationEnabled(true) //是否开启说话人分离功能
                    .diarization(parametersTranscriptionDiarization) //说话人分离功能参数
                    .outputLevel(2) //设置语音识别结果返回等级。默认值是 1: 1：识别出完整句子时返回识别结果； 2：识别出中间结果及完整句子时返回识别结果
                    .additionalStreamOutputLevel(2) //设置实时记录场景下活跃说话人对应的语音识别结果返回等级。 1：识别出完整句子时返回识别结果； 2：识别出中间结果及完整句子时返回识别结果；
                    .model("教育") //设置语音转写模型
                    .realtimeDiarizationEnabled(true) //启用实时日记
                    .build();
            CreateTaskRequest.Parameters parameters = CreateTaskRequest.Parameters.builder()
                    .transcription(parametersTranscription) //语音转写控制参数
                    .translation(parametersTranslation) //翻译功能开启时设置的参数
                    .translationEnabled(true) //是否启用翻译功能
                    .autoChaptersEnabled(true) //是否启用章节速览功能
                    .meetingAssistanceEnabled(true) //是否启用智能纪要功能
                    .summarizationEnabled(true) //是否启用摘要功能
                    .meetingAssistance(parametersMeetingAssistance) //智能纪要功能控制参数
                    .summarization(parametersSummarization) //摘要功能控制参数
                    .pptExtractionEnabled(false) //是否启用PPT抽取和PPT总结功能
                    .textPolishEnabled(false) //是否启用口语书面化功能
                    .serviceInspectionEnabled(false) //服务质检功能开关
                    .contentExtractionEnabled(true) //是否启用对话内容提取
                    .contentExtraction(parametersContentExtraction) //对话内容提取参数对象
                    .identityRecognitionEnabled(true) //身份识别开关
                    .identityRecognition(parametersIdentityRecognition) //身份识别参数对象
                    .build();

            //创建任务时设置的基本信息参数
            CreateTaskRequest.Input input = CreateTaskRequest.Input.builder()
                    .sourceLanguage("multilingual") //音频转写使用的语言模型
                    .taskKey(taskKey) //用户设置的自定义标识
                    .sampleRate(16000) //您创建实时会议时，需通过该参数指定音频流数据的采样率。当前支持 8000 和 16000。 8000：电话客服类场景 16000：实时会议音频采集场景
                    .format("pcm") //创建实时会议时，需通过该参数指定音频流数据的编码格式，比如 pcm。当前支持以下取值： pcm、opus、aac、speex、mp3
                    .progressiveCallbacksEnabled(false) //是否开启回调功能
                    .multipleStreamsEnabled(false) //是否开启多通道音频流识别
                    .languageHints(java.util.Arrays.asList(
                            "cn",
                            "en",
                            "yue",
                            "fspk",
                            "ja"
                    )) //偏好语种，仅当 SourceLanguage="multilingual"时生效，限制模型的输出语种
                    .build();

            //创建任务请求体
            CreateTaskRequest createTaskRequest = CreateTaskRequest.builder()
                    .input(input)
                    .appKey(ALI_TONGYITINGWU_APP_KEY)
                    .type("realtime") //任务类型： offline：表示离线任务，比如离线转写 realtime：表示实时任务，比如创建实时记录
                    .operation("start") //操作项，可选取值： start：表示任务的创建，也是默认值，通常情况下无须显示设置 stop：停止实时会议任务，对应的是创建实时会议，在会议结束后设置为 stop 并触发调用；实时会议场景使用； 需要注意：在结束实时记录时，务必设置此参数，且设置为 stop。
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

            //校验阿里返回结果
            RealTimeRecordingTaskOperateResp taskOperateResp = checkAliCreateTaskResponse(resp);

            return taskOperateResp;
        } catch (GlobalException globalException) {
            throw globalException;
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

    @Override
    public RealTimeRecordingTaskOperateResp stopRealTimeRecordingTask(RealTimeRecordingTaskOperateReq req) {
        AsyncClient client = null;
        try {
            String taskKey = req.getTaskKey();
            String taskId = req.getTaskId();

            //获取client
            client = getAsyncClient();

            // Parameter settings for API request
            CreateTaskRequest.Input input = CreateTaskRequest.Input.builder()
                    .taskId(taskId)
                    .sourceLanguage("multilingual")
                    .taskKey(taskKey)
                    .format("pcm")
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
            log.info("CreateTaskResponse : {}", new Gson().toJson(resp));
            // Asynchronous processing of return values
        /*response.thenAccept(resp -> {
            System.out.println(new Gson().toJson(resp));
        }).exceptionally(throwable -> { // Handling exceptions
            System.out.println(throwable.getMessage());
            return null;
        });*/

            //校验阿里返回结果
            RealTimeRecordingTaskOperateResp taskOperateResp = checkAliCreateTaskResponse(resp);

            return taskOperateResp;
        } catch (GlobalException globalException) {
            throw globalException;
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

    @Override
    public RealTimeRecordingTaskOperateResp getRealTimeRecordingTaskInfo(RealTimeRecordingTaskOperateReq req) {
        AsyncClient client = null;
        try {
            String taskKey = req.getTaskKey();
            String taskId = req.getTaskId();

            //获取client
            client = getAsyncClient();

            // Parameter settings for API request
            GetTaskInfoRequest getTaskInfoRequest = GetTaskInfoRequest.builder()
                    .taskId(taskId)
                    // Request-level configuration rewrite, can set Http request parameters, etc.
                    // .requestConfiguration(RequestConfiguration.create().setHttpHeaders(new HttpHeaders()))
                    .build();

            // Asynchronously get the return value of the API request
            CompletableFuture<GetTaskInfoResponse> response = client.getTaskInfo(getTaskInfoRequest);
            // Synchronously get the return value of the API request
            GetTaskInfoResponse resp = response.get();
            log.info("GetTaskInfoResponse : {}", new Gson().toJson(resp));
            // Asynchronous processing of return values
        /*response.thenAccept(resp -> {
            System.out.println(new Gson().toJson(resp));
        }).exceptionally(throwable -> { // Handling exceptions
            System.out.println(throwable.getMessage());
            return null;
        });*/

            //校验阿里返回响应
            RealTimeRecordingTaskOperateResp realTimeRecordingTaskOperateResp = checkAliGetTaskInfoResponse(resp);

            return realTimeRecordingTaskOperateResp;
        } catch (GlobalException globalException) {
            throw globalException;
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


    /**
     * 校验创建实时任务响应
     *
     * @param response
     * @return
     */
    private RealTimeRecordingTaskOperateResp checkAliCreateTaskResponse(CreateTaskResponse response) {
        if (Objects.isNull(response) || Objects.isNull(response.getBody())) {
            log.error("调用阿里通义听悟创建实时任务失败");
            throw new GlobalException(ResultCodeEnum.ALI_CLOUD_CALL_FAIL, "调用阿里通义听悟创建实时任务失败");
        }

        CreateTaskResponseBody responseBody = response.getBody();
        if (!"0".equals(responseBody.getCode())) {
            log.error("调用阿里通义听悟创建实时任务失败：" + responseBody.getMessage());
            throw new GlobalException(ResultCodeEnum.ALI_CLOUD_CALL_FAIL, "调用阿里通义听悟创建实时任务失败：" + responseBody.getMessage());
        }
        CreateTaskResponseBody.Data data = responseBody.getData();

        if (Objects.isNull(data)) {
            log.error("CreateTaskResponseBody.Data 为空， CreateTaskResponseBody ==》 {}", JSONObject.toJSONString(responseBody));
            throw new GlobalException(ResultCodeEnum.ALI_CLOUD_CALL_FAIL, "调用阿里通义听悟创建实时任务失败: " + JSONObject.toJSONString(responseBody));
        }

        //构造响应对象
        RealTimeRecordingTaskOperateResp taskResp = new RealTimeRecordingTaskOperateResp();
        taskResp.setMeetingJoinUrl(data.getMeetingJoinUrl());
        taskResp.setTaskId(data.getTaskId());
        taskResp.setTaskKey(data.getTaskKey());
        taskResp.setTaskStatus(data.getTaskStatus());

        return taskResp;
    }

    /**
     * 校验阿里获取任务响应体
     *
     * @param response
     * @return
     */
    private RealTimeRecordingTaskOperateResp checkAliGetTaskInfoResponse(GetTaskInfoResponse response) {
        if (Objects.isNull(response) || Objects.isNull(response.getBody())) {
            log.error("调用阿里通义听悟获取实时任务失败");
            throw new GlobalException(ResultCodeEnum.ALI_CLOUD_CALL_FAIL, "调用阿里通义听悟获取实时任务失败");
        }
        GetTaskInfoResponseBody responseBody = response.getBody();
        if (!"0".equals(responseBody.getCode())) {
            log.error("调用阿里通义听悟获取实时任务失败：" + responseBody.getMessage());
            throw new GlobalException(ResultCodeEnum.ALI_CLOUD_CALL_FAIL, "调用阿里通义听悟获取实时任务失败：" + responseBody.getMessage());
        }
        GetTaskInfoResponseBody.Data data = responseBody.getData();
        if (Objects.isNull(data) || Objects.isNull(data.getTaskStatus())) {
            log.error("调用阿里通义听悟获取实时任务失败 GetTaskInfoResponseBody:" + JSONObject.toJSONString(responseBody));
            throw new GlobalException(ResultCodeEnum.ALI_CLOUD_CALL_FAIL, "调用阿里通义听悟获取实时任务失败 GetTaskInfoResponseBody:" + JSONObject.toJSONString(responseBody));
        }
        /**
         * 任务状态。
         * NEW：新建任务
         * ONGOING：任务进行中。
         * COMPLETED：任务完成。
         * FAILED：任务失败
         * INVALID：无效任务。
         */
        String taskId = data.getTaskId();
        String taskStatus = data.getTaskStatus();
        if ("INVALID".equals(taskStatus)) {
            log.error("参数错误，无效任务, taskId:" + taskId);
            throw new GlobalException(ResultCodeEnum.PARAM_FAIL, "参数错误，无效任务, taskId:" + taskId);
        } else if ("FAILED".equals(taskStatus)) {
            log.error("任务失败, taskId:" + taskId);
            throw new GlobalException(ResultCodeEnum.FAIL, "任务失败, taskId:" + taskId);
        }

        //构造响应体
        RealTimeRecordingTaskOperateResp resp = new RealTimeRecordingTaskOperateResp();
        BeanUtils.copyProperties(data, resp);
        GetTaskInfoResponseBody.Result result = data.getResult();
        if (Objects.nonNull(result)) {
            RealTimeRecordingTaskResultResp realTimeRecordingTaskResultResp = new RealTimeRecordingTaskResultResp();
            BeanUtils.copyProperties(result, realTimeRecordingTaskResultResp);
            resp.setResult(realTimeRecordingTaskResultResp);
        }

        return resp;
    }

    private AsyncClient getAsyncClient() throws Exception {
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
        AsyncClient client = AsyncClient.builder()
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
        return client;
    }
}
