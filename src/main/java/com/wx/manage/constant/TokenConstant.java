package com.wx.manage.constant;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Description token常量
 * @Date 2023/8/31 14:02 星期四
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "TokenConstant", description = "token常量")
public class TokenConstant {

    public static final String AUTHORIZATION = "Authorization";
}
