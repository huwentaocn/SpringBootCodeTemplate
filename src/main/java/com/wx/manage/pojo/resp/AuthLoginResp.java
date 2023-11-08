package com.wx.manage.pojo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Description 用户登录响应体
 * @Date 2023/11/7 17:24 星期二
 * @Author Hu Wentao
 */

@ApiModel(description = "管理后台 - 登录 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthLoginResp {

    @ApiModelProperty(value = "用户编号", required = true, example = "1024")
    private Long userId;

    @ApiModelProperty(value = "访问令牌", required = true, example = "happy")
    private String accessToken;

    @ApiModelProperty(value = "刷新令牌", required = true, example = "nice")
    private String refreshToken;

    @ApiModelProperty(value = "过期时间", required = true)
    private LocalDateTime expiresTime;
}
