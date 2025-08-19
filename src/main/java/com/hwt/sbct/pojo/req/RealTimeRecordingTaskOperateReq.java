package com.hwt.sbct.pojo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 实时记录任务操作请求体
 * @Date 2025/6/11 13:55 星期三
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "RealTimeRecordingTaskOperateReq", description = "实时记录任务操作请求体")
public class RealTimeRecordingTaskOperateReq {

    @ApiModelProperty("用户设置的自定义标识")
    private String taskKey;

    @ApiModelProperty(value = "任务id")
    private String taskId;
}
