package com.hwt.sbct.exception;

import com.hwt.sbct.result.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 自定义全局异常实体
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-08-24
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Integer code;

    private String msg;

    private Object error;


    /**
     * 返回前端提示使用，全部默认为 fail = -1
     * @param msg 用户提示信息
     */
    public GlobalException(String msg){
        super(msg);
        this.code = ResultCodeEnum.FAIL.code;
        this.msg = msg;
    }

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
     * 返回前端提示使用，全部默认为 fail = -1
     * @param msg 用户提示信息
     * @param error 记录日志信息 error
     */
    public GlobalException(String msg, Object error){
        this.code = ResultCodeEnum.FAIL.code;
        this.msg = msg;
        this.error = error;
    }

    /**
     *
     * @param resultCodeEnum
     */
    public GlobalException(ResultCodeEnum resultCodeEnum){
        this.code = resultCodeEnum.code;
        this.msg = resultCodeEnum.message;
    }

    /**
     * 返回指定code码，详细错误信息
     * @param codeEnum
     * @param errorMessage
     */
    public GlobalException(ResultCodeEnum codeEnum, String errorMessage){
        this.code = codeEnum.getCode();
        this.msg = errorMessage;
    }

}
