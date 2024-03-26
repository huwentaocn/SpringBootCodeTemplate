package com.hwt.sbct.result;

import lombok.Data;

@Data
public class Result<T> {

    //返回码
    private Integer code;

    //返回消息
    private String message;

    //返回数据
    private T data;

    public Result(){}

    // 返回数据
    protected static <T> Result<T> build(T data) {
        Result<T> result = new Result<T>();
        if (data != null)
            result.setData(data);
        return result;
    }

    public static <T> Result<T> build(T body, Integer code, String message) {
        Result<T> result = build(body);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> build(T body, ResultCodeEnum resultCodeEnum) {
        Result<T> result = build(body);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    public static<T> Result<T> success(){
        return Result.success(null);
    }

    /**
     * 操作成功
     * @param data  baseCategory1List
     * @param <T>
     * @return
     */
    public static<T> Result<T> success(T data){
        Result<T> result = build(data);
        return build(data, ResultCodeEnum.SUCCESS);
    }

    public static<T> Result<T> fail(Integer code, String message){
        return build(null, code, message);
    }

    /**
     * 操作失败
     * @param data
     * @param <T>
     * @return
     */
    public static<T> Result<T> fail(T data){
        Result<T> result = build(data);
        return build(data, ResultCodeEnum.FAIL);
    }

    public static<T> Result<T> fail(){
        return Result.fail(null);
    }

    public static<T> Result<T> fail(ResultCodeEnum codeEnum){
        return build(null, codeEnum);
    }

    public static<T> Result<T> fail(ResultCodeEnum codeEnum, String errorMessage){
        return build(null, codeEnum.getCode(), codeEnum.getMessage() + "==>" + errorMessage);
    }

    public static<T> Result<T> fail(ResultCodeEnum codeEnum, T data){
        return build(data, codeEnum.getCode(), codeEnum.getMessage());
    }

    public Result<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }
}