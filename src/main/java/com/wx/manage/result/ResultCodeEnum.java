package com.wx.manage.result;

import com.alibaba.druid.wall.violation.ErrorCode;
import lombok.Getter;

/**
 * 统一返回结果状态信息类
 *
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"成功"),
    FAIL(-200, "失败"),

    /**
     * 系统故障 -1001 ~  -1999
     *
     */
    SYSTEM_FAIL(-1001, "服务异常"),

    SYSTEM_ERROR500(500,"系统内部错误"),

    /**
     * 参数故障  -2001 ~  -2999
     */
    PARAM_FAIL(-2001, "参数异常"),


    /**
     * 数据故障 -3001 ~  -3999
     */
    DATA_ERROR(-3000, "数据异常"),

    DATA_EXIST_FAIL(-3001, "数据已存在"),

    DATA_NOT_EXIST_FAIL(-3002, "数据不存在"),

    DATA_IS_USED_FAIL(-3003, "数据被占用"),

    DATA_NON_EMPTY(-3004, "数据不为空"),

    /**
     * 权限故障  使用 -9001 ~  -9999
     */
    LOGIN_AUTH(-4000, "未登陆"),
    TOKEN_PARSE_FAIL(-4001, "token校验失败"),

    PERMISSION_ERROR(-4002, "没有权限"),

    PASSWORD_ERROR(-4003, "密码错误"),

    SMS_CODE_INVALID_ERROR(-4004, "短信验证码无效"),

    SMS_CODE_ERROR(-4005, "短信验证码错误"),


    /**
     * 代码异常：-4001 ~ 4999
     */
    NULL_POINTER_EXCEPTION(-5001, "空指针异常"),

    RUNTIME_EXCEPTION(-5002, "运行时异常"),

    ILLEGAL_ARGUMENT_EXCEPTION(-5003, "非法数据异常"),

    REST_CLIENT_EXCEPTION(-5004, "服务调用异常"),

    BAD_SQL_GRAMMAR_EXCEPTION(-5005, "sql语法错误"),

    SERVLET_EXCEPTION(-5006, "servlet异常"),



    /**
     * 第三方故障  使用 -10001 ~  -19999
     */
    TRIPARTITE_FAIL(-10000, "三方异常"),

    SMS_SEND_FAIL(-10001, "短信发送失败"),

    WECHAT_CALL_FAIL(-10002, "微信调用失败"),

    WENXIANG_CALL_FAIL(-10003, "文香私有化平台调用失败"),


    // ========== AUTH 模块 1002000000 ==========
    AUTH_LOGIN_BAD_CREDENTIALS_ERROR(1002000000, "登录失败，账号密码不正确"),
    AUTH_LOGIN_USER_DISABLED_ERROR(1002000001, "登录失败，账号被禁用"),

    // ========== 角色模块 1002002000 ==========
    ROLE_NOT_EXISTS(1002002000, "角色不存在"),
    ROLE_NAME_DUPLICATE(1002002001, "已经存在名为【{}】的角色"),
    ROLE_CODE_DUPLICATE(1002002002, "已经存在编码为【{}】的角色"),
    ROLE_CAN_NOT_UPDATE_SYSTEM_TYPE_ROLE(1002002003, "不能操作类型为系统内置的角色"),
    ROLE_IS_DISABLE(1002002004, "名字为【{}】的角色已被禁用"),
    ROLE_ADMIN_CODE_ERROR(1002002005, "编码【{}】不能使用"),

    // ========== 用户模块 1002003000 ==========
    USER_USERNAME_EXISTS(1002003000, "用户账号已经存在"),
    USER_MOBILE_EXISTS(1002003001, "手机号已经存在"),
    USER_EMAIL_EXISTS(1002003002, "邮箱已经存在"),
    USER_NOT_EXISTS(1002003003, "用户不存在"),
    USER_IMPORT_LIST_IS_EMPTY(1002003004, "导入用户数据不能为空！"),
    USER_PASSWORD_FAILED(1002003005, "用户密码校验失败"),
    USER_IS_DISABLE(1002003006, "名字为【{}】的用户已被禁用"),
    USER_COUNT_MAX(1002003008, "创建用户失败，原因：超过租户最大租户配额({})！"),



    // ========== 数据源配置 1001007000 ==========
    DATA_SOURCE_CONFIG_NOT_EXISTS(1001007000, "数据源配置不存在"),

    DATA_SOURCE_CONFIG_ALREADY_EXISTS(1001007001, "数据源配置已经存在"),
    DATA_SOURCE_CONFIG_NOT_OK(1001007002, "数据源配置不正确，无法进行连接"),

    DATA_SOURCE_DATABASE_ALREADY_EXISTS(1001007003, "数据库已经存在"),
    DATA_SOURCE_CREATE_DATABASE_ERROR(1001007004, "数据源创建库失败"),

    DATA_SOURCE_INIT_TABLE_STRUCT_ERROR(1001007005, "数据源初始化表结构失败"),

    // ========== 租户信息 1002015000 ==========
    TENANT_NOT_EXISTS(1002015000, "租户不存在"),
    TENANT_DISABLE(1002015001, "名字为【{}】的租户已被禁用"),
    TENANT_EXPIRE(1002015002, "名字为【{}】的租户已过期"),
    TENANT_CAN_NOT_UPDATE_SYSTEM(1002015003, "系统租户不能进行修改、删除等操作！"),


    // ========== 租户套餐 1002016000 ==========
    TENANT_PACKAGE_NOT_EXISTS (1002016000, "租户套餐不存在"),
    TENANT_PACKAGE_USED (1002016001, "租户正在使用该套餐，请给租户重新设置套餐后再尝试删除"),
    TENANT_PACKAGE_DISABLE (1002016002, "名字为【{}】的租户套餐已被禁用"),


    ;

    private Integer code;

    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}