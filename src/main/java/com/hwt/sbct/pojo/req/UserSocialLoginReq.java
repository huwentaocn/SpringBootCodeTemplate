package com.hwt.sbct.pojo.req;

import com.aliyun.core.annotation.EnumType;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.hwt.sbct.constant.SocialType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Description 用户社交登录请求体
 * @Date 2025/8/20 15:32 星期三
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "UserSocialLoginReq", description = "用户社交登录请求体")
public class UserSocialLoginReq {

    @ApiModelProperty(value = "社交平台的类型，参见 UserSocialTypeEnum 枚举值", required = true, example = "qq")
    @NotNull(message = "社交平台的类型不能为空")
    private String type;

    @ApiModelProperty(value = "授权码", required = true, example = "1024")
    @NotEmpty(message = "授权码不能为空")
    private String code;

    @ApiModelProperty(value = "state", required = true, example = "9b2ffbc1-7425-4155-9894-9d5c08541d62")
    @NotEmpty(message = "state 不能为空")
    private String state;
}
