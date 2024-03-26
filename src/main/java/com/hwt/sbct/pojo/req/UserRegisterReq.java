package com.hwt.sbct.pojo.req;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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

    @ApiModelProperty("昵称")
    private String name;

    @ApiModelProperty("性别：1男，2女， 默认1")
    private Integer sex;

    @ApiModelProperty("头像id")
    private String headResourceId;

    @ApiModelProperty("账号状态（0正常，1停用），默认0")
    private Integer status;

    @ApiModelProperty("电话号码")
    private String mobile;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("备注")
    private String remark;


}
