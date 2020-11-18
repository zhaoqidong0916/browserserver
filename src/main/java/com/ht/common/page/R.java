package com.ht.common.page;

import com.ht.common.enums.ResponseEnum;

import java.io.Serializable;

import static com.ht.common.enums.ResponseEnum.ERROR;
import static com.ht.common.enums.ResponseEnum.SUCCESS;


/**
 * @date 2020/1/9 9:58
 * @desc 返回结果
 * @author yakun.shi
 */
public class R<T>  implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer result;
    private String message;
    private T data;

    public R(){}

    private R(T data) {
        this.setResult(SUCCESS.getCode());
        this.setMessage(SUCCESS.getMsg());
        this.data = data;
    }

    private R(Integer result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    private R(Integer code, String msg) {
        this.result = code;
        this.message = msg;
    }

    public static R success(Object data){
        return new R(data);
    }

    public static R success(){
        return new R(SUCCESS.getCode(),SUCCESS.getMsg(),null);
    }

    public static R result(ResponseEnum responseEnum, Object data){
        return new R(responseEnum.getCode(),responseEnum.getMsg(),data);
    }

    public static R result(ResponseEnum responseEnum){
        return new R(responseEnum.getCode(),responseEnum.getMsg(),null);
    }

    public static R error(){
        return new R(ERROR.getCode(),ERROR.getMsg());
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public R<T> setData(T data) {
        this.data = data;
        return this;
    }
}
