package com.hwt.sbct.pojo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 创建实时记录任务响应对象
 * @Date 2025/6/5 17:39 星期四
 * @Author Hu Wentao
 */
@Data
@ApiModel(value = "RealTimeRecordingTaskOperateResp", description = "创建实时记录任务响应对象")
public class RealTimeRecordingTaskOperateResp {

    @ApiModelProperty(value = "创建任务时生成的TaskId，用于查询任务状态、结果以及排查问题时使用。")
    private String taskId;

    @ApiModelProperty(value = "您创建任务时设置的TaskKey")
    private String taskKey;

    @ApiModelProperty(value = "实时记录场景下生成的音频流推送地址，您可以在后续实时音频流识别时通过该地址进行")
    private String meetingJoinUrl;

    @ApiModelProperty(value = "任务状态")
    private String taskStatus;

    @ApiModelProperty(value = "mp3 转换结果的 url 链接")
    private String outputMp3Path;

    @ApiModelProperty(value = "mp4 转换结果的 url 链接")
    private String outputMp4Path;

    @ApiModelProperty(value = "视频缩略图的 url 链接")
    private String outputSpectrumPath;

    @ApiModelProperty(value = "音频波形图的 url 链接")
    private String outputThumbnailPath;

    @ApiModelProperty(value = "实时记录任务结果")
    private RealTimeRecordingTaskResultResp result;
}
