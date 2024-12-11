package com.hwt.sbct.pojo.baidu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 百度接口返回对象
 * @Date 2024/12/11 16:47 星期三
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "BaiduResultVo", description = "百度接口返回对象")
public class BaiduResultVo {

    @ApiModelProperty("本次API访问状态，如果成功返回0，如果失败返回其他数字。")
    private Integer status;

    @ApiModelProperty("消息")
    private String msg;

    @ApiModelProperty("结果数据")
    private Object result;
}
