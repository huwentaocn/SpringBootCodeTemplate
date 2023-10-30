package com.wx.manage.exception;

import com.wx.manage.result.Result;
import com.wx.manage.result.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;


/**
 * <p>
 * 异常处理器
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-08-24
 */
@Slf4j
@Component
@RestControllerAdvice
public class GlobalExceptionHandler<T> {


    /**
     * 自定义异常 GlobalException
     * 用于前端弹出提示
     */
    @ExceptionHandler(value = {GlobalException.class})
    public Result<T> customHandleException(HttpServletRequest request, GlobalException e) {
        String method = request.getRequestURI();
        log.error(" method: " + method + " ====> 触发自定义异常！原因是:"+ e.getMsg());
        return Result.fail(e.getCode(), e.getMsg());
    }

    /**
     * 运行时异常 RuntimeException
     */
    @ExceptionHandler(value = RuntimeException.class)
    public Result<T> handleRuntimeException(HttpServletRequest request, RuntimeException e) {
        String method = request.getRequestURI();
        log.error(" method: " + method + " ====> 触发运行时未知异常！原因是：{} ",e);
        return Result.fail(ResultCodeEnum.RUNTIME_EXCEPTION);
    }


    /**
     * 传参异常
     * 注解 @Valid 和 @RequestBody 联合使用
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<T> handleValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        String method = request.getRequestURI();
        BindingResult bindingResult = e.getBindingResult();
        String error = BindingResultResponse.getError(bindingResult);
        log.error(" method: " + method + " ====> 触发入参异常！原因是:",error);
        return Result.fail(ResultCodeEnum.PARAM_FAIL);
    }

    /**
     * 数据异常 RuntimeException  FAIL(-1,"fail")
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result<T> handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException e) {
        String method = request.getRequestURI();
        log.error(" method: " + method + " ====> 触发数据异常！原因是:"+ e);
        return Result.fail(ResultCodeEnum.ILLEGAL_ARGUMENT_EXCEPTION);
    }


    /**
     * 空指针异常 ：  SYSTEM_UNKNOWN_FAIL(-1002, "System unknown fault"),  // 系统未知
     */
    @ExceptionHandler(value = NullPointerException.class)
    public Result<T> handleNullPointerException(HttpServletRequest request, NullPointerException e) {
        String method = request.getRequestURI();
        log.error(" method: " + method + " ====> 触发空指针异常！原因是:",e);
        return Result.fail(ResultCodeEnum.NULL_POINTER_EXCEPTION);
    }


    /**
     * Controller上一层相关异常
     */
    @ExceptionHandler({
            NoHandlerFoundException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            HttpMediaTypeNotAcceptableException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            MissingServletRequestPartException.class,
            AsyncRequestTimeoutException.class
    })
    public Result<T> handleServletException(HttpServletRequest request, Exception e) {
        String method = request.getRequestURI();
        log.error(" method: " + method + " ====> 触发Controller上一层相关异常异常！原因是:",e);
        //SERVLET_ERROR(-102, "servlet请求异常"),
        return Result.fail(ResultCodeEnum.SERVLET_EXCEPTION);
    }






    /**
     * 调用异常:TRIPARTITE_FAIL(-10001, "Three parties fault")
     */
    @ExceptionHandler(value = RestClientException.class)
    public Result<T> handleRestClientException(HttpServletRequest request, RestClientException e) {
        String method = request.getRequestURI();
        log.error(" method: " + method + " ====> 触发 Rest 调用异常！原因是:",e);
        return Result.fail(ResultCodeEnum.REST_CLIENT_EXCEPTION);
    }

    /**
     * 数据sql语法异常
     */
    @ExceptionHandler(value = BadSqlGrammarException.class)
    public Result<T> handleException(HttpServletRequest request, BadSqlGrammarException e){
        String method = request.getRequestURI();
        log.error(" method: " + method + " ====> 触发数据sql语法异常！原因是:",e);
        return Result.fail(ResultCodeEnum.BAD_SQL_GRAMMAR_EXCEPTION);
    }


    /**
     * 系统未知异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Result<T> handleException(HttpServletRequest request, Exception e) {
        String method = request.getRequestURI();
        log.error(" method: " + method + " ====> 触发系统未知异常异常！原因是:",e);
        //SERVLET_ERROR(-102, "servlet请求异常"),
        return Result.fail(ResultCodeEnum.SYSTEM_FAIL);
    }


}


