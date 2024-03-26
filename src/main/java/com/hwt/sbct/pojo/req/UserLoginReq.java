package com.hwt.sbct.pojo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Description 用户密码登录请求体
 * @Date 2023/8/28 15:40 星期一
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "UserLoginReq", description = "用户密码登录请求体")
public class UserLoginReq {

    @NotNull(message = "登录类型必传")
    @ApiModelProperty(name = "type", value = "登录类型：1密码登录，2短信验证码登录")
    private Integer type;

    @NotBlank(message = "用户名称必传")
    @ApiModelProperty(name = "userName", value = "用户名")
    private String userName;

    @NotBlank(message = "密码/验证码必传")
    @ApiModelProperty(name = "password", value = "密码/验证码")
    private String password;
}
