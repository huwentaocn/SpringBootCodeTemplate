package com.hwt.sbct.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 统一返回结果状态信息类
 *
 */
@Getter
@AllArgsConstructor
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

    SOCIAL_USER_AUTH_FAIL(-11000, "社交授权失败"),

    SOCIAL_USER_NOT_EXIST_FAIL(-11001, "社交用户不存在"),


    SOCIAL_USER_NOT_BIND_FAIL(-11002, "社交用户未绑定平台用户"),

    SOCIAL_USER_ALREADY_BIND_FAIL(-11003, "社交用户已经绑定平台用户"),

    ;

    public Integer code;

    public String message;

}