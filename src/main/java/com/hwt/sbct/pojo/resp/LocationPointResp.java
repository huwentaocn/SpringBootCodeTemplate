package com.hwt.sbct.pojo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 经纬度对象
 * @Date 2024/12/11 16:54 星期三
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "LocationPointResp", description = "经纬度对象")
public class LocationPointResp {

    @ApiModelProperty("经度")
    private String longitude;

    @ApiModelProperty("纬度")
    private String latitude;
}
