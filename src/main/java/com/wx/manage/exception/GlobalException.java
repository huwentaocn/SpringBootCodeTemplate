package com.wx.manage.exception;

import com.wx.manage.result.ResultCodeEnum;
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
     * 返回指定code码，详细错误信息
     * @param codeEnum
     * @param errorMessage
     */
    public GlobalException(ResultCodeEnum codeEnum, String errorMessage){
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMessage() + "==>" + errorMessage;
    }
}
