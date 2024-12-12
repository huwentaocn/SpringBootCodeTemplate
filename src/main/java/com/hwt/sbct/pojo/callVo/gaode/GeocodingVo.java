package com.hwt.sbct.pojo.callVo.gaode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 地理编码接口响应
 * @Date 2024/12/11 17:10 星期三
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "GeocodingVo", description = "地理编码接口响应")
public class GeocodingVo {

    @ApiModelProperty("国家")
    private String country;

    @ApiModelProperty("地址所在的省份名")
    private String province;

    @ApiModelProperty("地址所在的城市名")
    private String city;

    @ApiModelProperty("城市编码")
    private String citycode;

    @ApiModelProperty("地址所在的区")
    private String district;

    @ApiModelProperty("街道")
    private String street;

    @ApiModelProperty("门牌")
    private String number;

    @ApiModelProperty("区域编码")
    private String adcode;

    @ApiModelProperty("坐标点")
    private String location;

    @ApiModelProperty("匹配级别")
    private String level;
}
