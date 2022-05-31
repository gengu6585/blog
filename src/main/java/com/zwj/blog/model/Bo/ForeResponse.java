package com.zwj.blog.model.Bo;

/**
 * rest返回对象
 * ^
 *
 * @param <T>
 */
public class ForeResponse<T> {

    /**
     * 服务器响应数据
     */
    private T payload;

    /**
     * 请求是否成功
     */
    private boolean success;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 状态码
     */
    private int code = -1;

    /**
     * 服务器响应时间
     */
    private long timestamp;

    public ForeResponse() {
        this.timestamp = System.currentTimeMillis() / 1000;
    }

    public ForeResponse(boolean success) {
        this.timestamp = System.currentTimeMillis() / 1000;
        this.success = success;
    }

    public ForeResponse(boolean success, T payload) {
        this.timestamp = System.currentTimeMillis() / 1000;
        this.success = success;
        this.payload = payload;
    }

    public ForeResponse(boolean success, T payload, int code) {
        this.timestamp = System.currentTimeMillis() / 1000;
        this.success = success;
        this.payload = payload;
        this.code = code;
    }

    public ForeResponse(boolean success, String msg) {
        this.timestamp = System.currentTimeMillis() / 1000;
        this.success = success;
        this.msg = msg;
    }

    public ForeResponse(boolean success, String msg, int code) {
        this.timestamp = System.currentTimeMillis() / 1000;
        this.success = success;
        this.msg = msg;
        this.code = code;
    }

    public static ForeResponse success() {
        return new ForeResponse(true);
    }

    public static <T> ForeResponse success(T payload) {
        return new ForeResponse(true, payload);
    }
    public static <T> ForeResponse fail(T payload,String message,int code) {
        return new ForeResponse(true, payload,code);
    }

    public static <T> ForeResponse success(int code) {
        return new ForeResponse(true, null, code);
    }

    public static <T> ForeResponse success(T payload, int code) {
        return new ForeResponse(true, payload, code);
    }

    public static ForeResponse fail() {
        return new ForeResponse(false);
    }

    public static ForeResponse fail(String msg) {
        return new ForeResponse(false, msg);
    }

    public static ForeResponse fail(int code) {
        return new ForeResponse(false, null, code);
    }

    public static ForeResponse fail(int code, String msg) {
        return new ForeResponse(false, msg, code);
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}