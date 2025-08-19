package com.hwt.sbct.pojo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 实时记录任务结果信息
 * @Date 2025/6/12 9:28 星期四
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "RealTimeRecordingTaskResultResp", description = "实时记录任务结果信息")
public class RealTimeRecordingTaskResultResp {

    @ApiModelProperty(value ="语音转写对应的结果url链接")
    private String transcription;

    @ApiModelProperty(value ="章节速览功能对应的结果 url 链接")
    private String autoChapters;

    @ApiModelProperty(value ="智能纪要对应的结果 url 链接")
    private String meetingAssistance;

    @ApiModelProperty(value ="大模型摘要对应的结果 url 链接")
    private String summarization;

    @ApiModelProperty(value ="文本翻译对应的结果 url 链接")
    private String translation;

    @ApiModelProperty(value ="视频 PPT 抽取和总结对应的结果 url 链接")
    private String pptExtraction;

    @ApiModelProperty(value ="口语书面化对应的结果 url 链接")
    private String textPolish;

    @ApiModelProperty(value ="自定义 prompt 对应的结果 url 链接")
    private String customPrompt;

    @ApiModelProperty(value ="服务质检对应的结果 url 链接")
    private String serviceInspection;

    @ApiModelProperty(value ="身份识别对应的结果 url 链接")
    private String identityRecognition;

    @ApiModelProperty(value ="对话内容提取对应的结果 url 链接")
    private String contentExtraction;
}
