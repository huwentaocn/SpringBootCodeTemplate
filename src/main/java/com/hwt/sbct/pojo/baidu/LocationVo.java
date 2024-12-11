package com.hwt.sbct.pojo.baidu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 经纬度对象
 * @Date 2024/12/11 16:53 星期三
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "LocationVo", description = "经纬度对象")
public class LocationVo {

    @ApiModelProperty("经度")
    private String lng;

    @ApiModelProperty("纬度")
    private String lat;
}
