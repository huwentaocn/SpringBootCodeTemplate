package com.wx.manage.pojo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description 获取微信用户电话号码请求体
 * @Date 2023/8/31 15:27 星期四
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "GetWechatUserPhoneReq", description = "获取微信用户电话号码请求体")
public class GetWechatUserPhoneReq {

    @NotBlank(message = "手机号获取凭证必传")
    @ApiModelProperty(name = "code", value = "手机号获取凭证")
    private String code;

    @NotBlank(message = "微信用户唯一凭证必传")
    @ApiModelProperty(name = "openid", value = "微信用户唯一凭证")
    private String openid;
}
