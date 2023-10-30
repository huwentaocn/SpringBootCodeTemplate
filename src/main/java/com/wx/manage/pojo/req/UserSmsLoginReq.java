package com.wx.manage.pojo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description 用户短信登录请求体
 * @Date 2023/8/29 10:41 星期二
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "UserSmsLoginReq", description = "用户短信登录请求体")
public class UserSmsLoginReq {

    @NotBlank(message = "用户名称必传")
    @ApiModelProperty(name = "userName", value = "用户名")
    private String userName;

    @NotBlank(message = "验证码必传")
    @ApiModelProperty(name = "code", value = "验证码")
    private String code;
}
