package com.wx.manage.pojo.req;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Description 注册请求体
 * @Date 2023/8/29 9:24 星期二
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "UserRegisterReq", description = "注册请求体")
public class UserRegisterReq {

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty("用户名")
    private String userName;

    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty("验证码")
    private String code;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty("密码")
    private String password;

    @NotNull(message = "性别不能为空")
    @ApiModelProperty("性别：1男，2女，默认1")
    private Integer sex;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("微信openid")
    private String openId;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("头像")
    private String headUrl;


}
