package com.card.alumni.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sunxiaodong10 2019/12/7
 * @date 3:42 PM
 */
@Getter
@AllArgsConstructor
public enum ResultCodeEnum {
    SUCCESS(0, "成功"),
    PARAM_EMPTY(1, "参数为空"),
    PARAM_ERROR(2, "参数错误，请检查"),
    SYS_ERROR(500, "系统内部错误"),
    ;
    private int code;
    private String errorMsg;
}
