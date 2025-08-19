package com.hwt.sbct;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.nls.client.protocol.InputFormatEnum;
import com.alibaba.nls.client.protocol.NlsClient;
import com.alibaba.nls.client.protocol.SampleRateEnum;
import com.alibaba.nls.client.protocol.asr.SpeechTranscriber;
import com.alibaba.nls.client.protocol.asr.SpeechTranscriberListener;
import com.alibaba.nls.client.protocol.asr.SpeechTranscriberResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.Arrays;

/**
 * @author tingwu2023
 * @desc 演示了实时会议场景，在创建会议后，根据返回的MeetingJoinUrl进行实时语音识别的 调用。
 */

@Slf4j
public class RealtimeTransTest {

    private static NlsClient NLS_CLIENT;

    /**
     * 初始化语音识别SDK，可复用，可全局使用
     */
    @BeforeClass
    public static void before() {
        NLS_CLIENT = new NlsClient("default");
    }

    /**
     * 单路实时转写，绝大多数情况下，您可只参考此处实现即可。
     */
    @Test
    public void testRealtimeTrans() throws Exception {
        // 此处url来自于用户通过OpenAPI创建会议时返回的推流url， url地址一般是："wss://tingwu-realtime-cn-beijing.aliyuncs.com/api/ws/v1?mc={xxxxxxzzzzyyy}"
        String meetingJoinUrl = "ws://172.16.1.217:10096/111111";

        SpeechTranscriber speechTranscriber = new SpeechTranscriber(NLS_CLIENT, "default" ,createListener(), meetingJoinUrl);
//        /**--------------------*/
        //speechTranscriber.setAppKey(appKey);
        // 输入音频编码方式
//        speechTranscriber.setFormat(InputFormatEnum.PCM);
//        // 输入音频采样率
//        speechTranscriber.setSampleRate(SampleRateEnum.SAMPLE_RATE_16K);
//        // 是否返回中间识别结果
//        speechTranscriber.setEnableIntermediateResult(true);
//        // 是否生成并返回标点符号
//        speechTranscriber.setEnablePunctuation(true);
//        // 是否将返回结果规整化,比如将一百返回为100
//        speechTranscriber.setEnableITN(false);
        speechTranscriber.start();

        // 使用本地文件模拟 真实场景下音频流实时采集
        String localAudioFile = "src/main/resources/vad_example.wav";
//        String localAudioFile = "src/main/resources/xc.wav";
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

}