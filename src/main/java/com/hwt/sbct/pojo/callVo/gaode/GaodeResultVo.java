package com.hwt.sbct.pojo.callVo.gaode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description 高德接口返回对象
 * @Date 2024/12/11 16:47 星期三
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "GaodeResultVo", description = "高德接口返回对象")
public class GaodeResultVo {

    @ApiModelProperty("返回结果状态值")
    private Integer status;

    @ApiModelProperty("返回结果数目")
    private Integer count;

    @ApiModelProperty("返回状态说明")
    private String info;

    @ApiModelProperty("结果数据")
    private List<GeocodingVo> geocodes;
}
