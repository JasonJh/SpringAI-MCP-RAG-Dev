package com.wjh.utils;

public class WjhResult {

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    private String ok;	// 不使用

    public static WjhResult build(Integer status, String msg, Object data) {
        return new WjhResult(status, msg, data);
    }

    public static WjhResult build(Integer status, String msg, Object data, String ok) {
        return new WjhResult(status, msg, data, ok);
    }

    public static WjhResult ok(Object data) {
        return new WjhResult(data);
    }

    public static WjhResult ok() {
        return new WjhResult(null);
    }

    public static WjhResult errorMsg(String msg) {
        return new WjhResult(500, msg, null);
    }

    public static WjhResult errorUserTicket(String msg) {
        return new WjhResult(557, msg, null);
    }

    public static WjhResult errorMap(Object data) {
        return new WjhResult(501, "error", data);
    }

    public static WjhResult errorTokenMsg(String msg) {
        return new WjhResult(502, msg, null);
    }

    public static WjhResult errorException(String msg) {
        return new WjhResult(555, msg, null);
    }

    public static WjhResult errorUserQQ(String msg) {
        return new WjhResult(556, msg, null);
    }

    public WjhResult() {

    }

    public WjhResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public WjhResult(Integer status, String msg, Object data, String ok) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.ok = ok;
    }

    public WjhResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isOK() {
        return this.status == 200;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

}
