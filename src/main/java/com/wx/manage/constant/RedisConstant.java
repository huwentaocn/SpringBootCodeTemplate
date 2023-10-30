package com.wx.manage.constant;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.text.MessageFormat;

/**
 * @Description redis常量类
 * @Date 2023/8/29 9:48 星期二
 * @Author Hu Wentao
 */
@Data
@ApiModel(value = "RedisConstant", description = "redis常量类")
public class RedisConstant {

    /**
     * 用户信息缓存超时时间
     */
    public static final int USER_KEY_TIMEOUT = 365;

    /**
     * 短信验证码key
     */
    private static final String SMS_CAPTCHA_KEY = "sms:captcha:{0}";

    /**
     * 用户信息key
     */
    private static final String USER_INFO_KEY = "user:info:{0}";


    /**
     * 获取短信验证码key
     * @param phone
     * @return
     */
    public static String getSmsCaptchaKey(String phone) {
        return MessageFormat.format(SMS_CAPTCHA_KEY, phone);
    }

    /**
     * 获取用户信息key
     * @param userId
     * @return
     */
    public static String getUserInfoKey(Long userId) {
        return MessageFormat.format(USER_INFO_KEY, userId);
    }
}
