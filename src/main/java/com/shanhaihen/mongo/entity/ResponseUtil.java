package com.shanhaihen.mongo.entity;

import java.io.Serializable;

public class ResponseUtil<T> implements Serializable {
    private static final long serialVersionUID = -2898420096528176859L;

    public static final Integer OK = 200;

    public static final Integer ERROR = 1;

    private T datas;

    private Integer resultCode;

    private String resultMsg;


    private ResponseUtil(T data, Integer code, String message) {
        this.datas = data;
        this.resultCode = code;
        this.resultMsg = message;
    }

    public static <T> ResponseUtil<T> success() {
        return success(null, null);
    }

    public static <T> ResponseUtil<T> success(T datas) {
        return success(datas, null);
    }

    public static <T> ResponseUtil<T> success(T datas, String message) {
        return new ResponseUtil<T>(datas, OK, message);
    }

    public static <T> ResponseUtil<T> failure(String message) {
        return failure(null, message);
    }

    public static <T> ResponseUtil<T> failure(Integer code, String message) {
        return failure(null, code, message);
    }

    public static <T> ResponseUtil<T> failure(T datas, String message) {
        return failure(datas, ERROR, message);
    }

    public static <T> ResponseUtil<T> failure(T datas, Integer code, String message) {
        return new ResponseUtil<T>(datas, code, message);
    }

    public T getDatas() {
        return datas;
    }

    public void setDatas(T data) {
        this.datas = data;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public boolean isSuccess() {
        return OK.equals(this.resultCode);
    }

}
