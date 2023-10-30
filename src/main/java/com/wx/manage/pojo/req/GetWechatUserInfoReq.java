package com.wx.manage.pojo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description 获取微信用户信息请求体
 * @Date 2023/9/1 10:41 星期五
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "GetWechatUserInfoReq", description = "获取微信用户信息请求体")
public class GetWechatUserInfoReq {

    @NotBlank(message = "sessionKey必传")
    @ApiModelProperty(name = "sessionKey", value = "sessionKey")
    private String sessionKey;

    @NotBlank(message = "encryptedData必传")
    @ApiModelProperty(name = "encryptedData", value = "encryptedData")
    private String encryptedData;

    @NotBlank(message = "iv必传")
    @ApiModelProperty(name = "iv", value = "iv")
    private String iv;
}
