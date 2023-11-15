package com.wx.manage.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description 角色标识枚举
 * @Date 2023/11/10 14:10 星期五
 * @Author Hu Wentao
 */
@Getter
@AllArgsConstructor
public enum RoleCodeEnum {

    SUPER_ADMIN("super_admin", "超级管理员"),
    TENANT_ADMIN("tenant_admin", "租户管理员"),
    ;

    /**
     * 角色编码
     */
    private final String code;
    /**
     * 名字
     */
    private final String name;

    public static boolean isSuperAdmin(String code) {
        return SUPER_ADMIN.code.equals(code);
    }

}
