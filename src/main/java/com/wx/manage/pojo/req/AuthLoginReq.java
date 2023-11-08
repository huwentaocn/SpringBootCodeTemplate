package com.wx.manage.pojo.req;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.github.xiaoymin.knife4j.core.util.StrUtil;
import com.wx.manage.constant.SocialTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @Description 账号密码登录请求体
 * @Date 2023/11/7 17:11 星期二
 * @Author Hu Wentao
 */
@Data
@ApiModel(value = "AuthLoginReq", description = "账号密码登录请求体")
public class AuthLoginReq {

    @NotEmpty(message = "登录账号不能为空")
    @Length(min = 4, max = 16, message = "账号长度为 4-16 位")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "账号格式为数字以及字母")
    @ApiModelProperty(name = "username", value = "用户名")
    private String username;

    @NotEmpty(message = "密码不能为空")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    @ApiModelProperty(name = "password", value = "密码")
    private String password;

    // ========== 图片验证码相关 ==========

    @NotEmpty(message = "验证码不能为空", groups = CodeEnableGroup.class)
    @ApiModelProperty(name = "captchaVerification", value = "验证码")
    private String captchaVerification;

    // ========== 绑定社交登录时，需要传递如下参数 ==========

    @ApiModelProperty(value = "社交平台的类型，参见 SysUserSocialTypeEnum 枚举值", required = true, example = "10")
    private Integer socialType;

    @ApiModelProperty(value = "授权码", required = true, example = "1024")
    private String socialCode;

    @ApiModelProperty(value = "state", required = true, example = "9b2ffbc1-7425-4155-9894-9d5c08541d62")
    private String socialState;

    /**
     * 开启验证码的 Group
     */
    public interface CodeEnableGroup {}

}
