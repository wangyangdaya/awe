package com.awe.web.core;

import com.awe.core.throwable.ErrorCode;
import com.awe.core.throwable.GeneralException;

/**
 * description 请求的响应结果
 *
 * @author wangy QQ 837195190
 * <p>Created by DELL-5490 on 2018/6/9.</p>
 */
public class RestResponse {
    private int code;
    private String message;
    private Object data;

    private RestResponse(ErrorCode errorCode) {
        ErrorCode c = errorCode.getCode() >= 400 || errorCode.getCode() == 200 ? errorCode : ErrorCode.INTERNAL_ERROR;
        this.code = c.getCode();
        this.message = c.getDesc();
    }

    private RestResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static RestResponse ok() {
        return new RestResponse(ErrorCode.OK);
    }

    public static RestResponse ok(Object data) {
        return new RestResponse(200, "OK", data);
    }

    public static RestResponse error(GeneralException exception) {
        RestResponse response = new RestResponse(exception.getExceptionType());
        response.setMessage(exception.getSimpleMessage());
        return response;
    }

    public static RestResponse error(ErrorCode errorCode) {
        return new RestResponse(errorCode);
    }

    public RestResponse setCodeAndMsg(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getDesc();
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
