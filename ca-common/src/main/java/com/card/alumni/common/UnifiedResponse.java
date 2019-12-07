package com.card.alumni.common;


import com.card.alumni.exception.ResultCodeEnum;
import com.card.alumni.exception.ResultCodeInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一响应体
 *
 * @author liumingyu
 * @date 2019-11-19 10:32 PM
 */
public class UnifiedResponse {

    private int status;

    private String message;

    private Object data;

    public UnifiedResponse() {
        this(ResultCodeEnum.SUCCESS);
    }

    public UnifiedResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public UnifiedResponse(Object data) {
        this(ResultCodeEnum.SUCCESS);
        if (data instanceof Long) {
            Map<String, Object> attachment = new HashMap<>();
            attachment.put("id", data);
            this.data = attachment;
        } else {
            this.data = data;
        }
    }

    public UnifiedResponse(ResultCodeEnum resultCodeEnum) {
        this.status = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getErrorMsg();
    }

    public UnifiedResponse(int status, String message, Object data) {
        this(status, message);
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
