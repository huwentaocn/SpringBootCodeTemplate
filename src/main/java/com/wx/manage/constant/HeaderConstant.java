package com.wx.manage.constant;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Description token常量
 * @Date 2023/8/31 14:02 星期四
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "HeaderConstant", description = "请求头常量")
public class HeaderConstant {

    public static final String AUTHORIZATION = "Authorization";


    /**
     * 租户id
     */
    public static final String HEADER_TENANT_ID = "tenant-id";
}
