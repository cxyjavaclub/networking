package com.run.util;

import java.io.Serializable;

public class JsonResponseUtils implements Serializable {

    private Integer code;// 状态码

    private String msg = "";

    private Object data = null;

    public JsonResponseUtils() {
    }

    private JsonResponseUtils(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private JsonResponseUtils(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static JsonResponseUtils ok() {
        return new JsonResponseUtils(200, "OK", null);
    }

    public static JsonResponseUtils ok(String msg) {
        return new JsonResponseUtils(200, msg, null);
    }

    public static JsonResponseUtils ok(Object data) {
        return new JsonResponseUtils(200, "", data);
    }

    public static JsonResponseUtils ok(String msg, Object data) {
        return new JsonResponseUtils(200, msg, data);
    }

    public static JsonResponseUtils err() {
        return new JsonResponseUtils(500, "Err", null);
    }

    public static JsonResponseUtils err(String msg) {
        return new JsonResponseUtils(500, msg, null);
    }

    public static JsonResponseUtils err(Object data) {
        return new JsonResponseUtils(500, "", data);
    }

    public static JsonResponseUtils err(String msg, Object data) {
        return new JsonResponseUtils(500, msg, data);
    }


    public static JsonResponseUtils biud(int code, String msg, Object data) {
        return new JsonResponseUtils(code, msg, data);
    }

    public static JsonResponseUtils biud(int code, String msg) {
        return new JsonResponseUtils(code, msg, null);
    }

    public static JsonResponseUtils biud(int code, Object data) {
        return new JsonResponseUtils(code, "", data);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JsonResponseUtils{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
