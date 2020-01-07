package com.card.alumni.common;

import com.card.alumni.exception.ResultCodeEnum;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 8:45 PM
 */
/**
 * 返回结果代码
 */
public class UnifiedResult<T> implements Serializable {

    /**
     * uid
     */
    private static final long serialVersionUID = 63997751149558245L;

    /**
     * 是否成功
     */
    private boolean success =false;

    /**
     * 响应码
     */
    private Integer status;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private T data;




    public UnifiedResult() {
        this.success = false;
        this.message = "统一返回异常!";
        this.status = ResultCodeEnum.SYS_ERROR.getCode();
    }

    /**
     * success
     *
     * @param <T>
     * @return
     */
    public static <T> UnifiedResult<T> success() {
        return success(null);
    }

    /**
     * success
     * @param data
     * @param <T>
     * @return
     */
    public static <T> UnifiedResult<T> success(T data) {
        UnifiedResult<T> r = new UnifiedResult<T>();
        r.setSuccess(true);
        r.setStatus(ResultCodeEnum.SUCCESS.getCode());
        r.setData(data);
        r.setMessage("成功!");
        return r;
    }

    /**
     * 失败
     *
     * @param code
     * @param <T>
     * @return
     */
    public static <T> UnifiedResult<T> failure(Integer code) {
        return failure(code, null);
    }


    /**
     *
     * @param errorCodeEnum
     * @param <T>
     * @return
     */
    public static <T> UnifiedResult<T> failure(ResultCodeEnum errorCodeEnum) {
        return failure(errorCodeEnum.getCode(), errorCodeEnum.getErrorMsg());
    }

    /**
     * 失败
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static <T> UnifiedResult<T> failure(Integer code, String message) {
        UnifiedResult result = new UnifiedResult();
        result.setStatus(code);
        result.setMessage(message);
        return result;
    }


    public UnifiedResult<T> succeed() {
        this.status = ResultCodeEnum.SUCCESS.getCode();
        this.message = "成功";
        this.success = true;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public void setData(T data) {
        this.data = data;
    }

}
