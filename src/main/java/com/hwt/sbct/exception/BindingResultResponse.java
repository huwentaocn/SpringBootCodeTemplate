package com.hwt.sbct.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * author : wmm
 * date : 2020-03-30 17:21
 * description : 封装请求错误信息返回参数
 * @author lh
 */

/**
 * <p>
 * 封装请求错误信息返回参数
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-08-24
 */
public class BindingResultResponse {

    public static String getError(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        String error = null;
        for (FieldError fieldError : fieldErrors) {
            error = fieldError.getDefaultMessage();
        }
        return error;
    }
}
