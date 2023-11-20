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
     * 租户内用户信息key
     */
    private static final String TENANT_USER_INFO_KEY = "tenant:{0}:user:info:{1}";

    /**
     * 拥有指定菜单的角色编号的缓存
     *
     * KEY 格式：user_role_ids:{menuId}
     * VALUE 数据类型：String 角色编号集合
     */
    String MENU_ROLE_ID_LIST = "menu_role_ids";


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

    /**
     * 获取租户用户信息key
     * @param tenantId
     * @param userId
     * @return
     */
    public static String getTenantUserInfoKey(Long tenantId, Long userId) {
        return MessageFormat.format(TENANT_USER_INFO_KEY, tenantId,  userId);
    }
}
