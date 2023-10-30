package com.wx.manage.constant;

import io.swagger.annotations.ApiModel;

/**
 * @Description 微信api常量
 * @Date 2023/8/29 17:34 星期二
 * @Author Hu Wentao
 */

@ApiModel(value = "WechatApiConstant", description = "微信api常量")
public class WechatApiConstant {

    /**
     * 微信域名
     */
    public static final String WECHAT_DOMAIN = "https://api.weixin.qq.com";

    /**
     * 小程序登录
     */
    public static final String CODE2SESSION_URL = WECHAT_DOMAIN + "/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
}
