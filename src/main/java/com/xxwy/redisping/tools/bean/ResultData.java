package com.xxwy.redisping.tools.bean;

/**
 * * From  xxwy
 */
public class ResultData<T> {

    private String msg;
    private String code;
    private T data = null;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResultData(String msg, String code, T data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public ResultData(String msg, String code) {
        this.msg = msg;
        this.code = code;
    }
}
