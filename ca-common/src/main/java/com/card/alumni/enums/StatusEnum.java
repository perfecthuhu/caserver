package com.card.alumni.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sunxiaodong10 2019/12/15
 * @date 2:57 PM
 */
@AllArgsConstructor
@Getter
public enum  StatusEnum {
    YES (1, "有效"),
    NO (2, "无效")
    ;
    private Integer code;
    private String desc;
}
