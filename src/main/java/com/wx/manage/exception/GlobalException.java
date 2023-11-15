package com.wx.manage.exception;

import com.google.common.annotations.VisibleForTesting;
import com.wx.manage.result.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 自定义全局异常实体
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-08-24
 */
@Data
@Slf4j
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int code;

    private String msg;


    /**
     * 返回前端提示使用，全部默认为 fail = -1
     * @param code 状态码
     * @param msg  用户提示信息
     */
    public GlobalException(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    /**
     *
     * @param resultCodeEnum
     */
    public GlobalException(ResultCodeEnum resultCodeEnum){
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMessage();
    }

    /**
     *
     * @param resultCodeEnum
     */
    public GlobalException(ResultCodeEnum resultCodeEnum, Object... params){
        this.code = resultCodeEnum.getCode();
        this.msg = doFormat(resultCodeEnum.getCode(), resultCodeEnum.getMessage(), params);
    }




    // ========== 格式化方法 ==========

    /**
     * 将错误编号对应的消息使用 params 进行格式化。
     *
     * @param code           错误编号
     * @param messagePattern 消息模版
     * @param params         参数
     * @return 格式化后的提示
     */
    @VisibleForTesting
    public static String doFormat(int code, String messagePattern, Object... params) {
        StringBuilder sbuf = new StringBuilder(messagePattern.length() + 50);
        int i = 0;
        int j;
        int l;
        for (l = 0; l < params.length; l++) {
            j = messagePattern.indexOf("{}", i);
            if (j == -1) {
                log.error("[doFormat][参数过多：错误码({})|错误内容({})|参数({})", code, messagePattern, params);
                if (i == 0) {
                    return messagePattern;
                } else {
                    sbuf.append(messagePattern.substring(i));
                    return sbuf.toString();
                }
            } else {
                sbuf.append(messagePattern, i, j);
                sbuf.append(params[l]);
                i = j + 2;
            }
        }
        if (messagePattern.indexOf("{}", i) != -1) {
            log.error("[doFormat][参数过少：错误码({})|错误内容({})|参数({})", code, messagePattern, params);
        }
        sbuf.append(messagePattern.substring(i));
        return sbuf.toString();
    }
}
