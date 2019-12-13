package com.card.alumni.vo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sunxiaodong10 2019/12/12
 * @date 8:37 PM
 */
@AllArgsConstructor
@Getter
public enum AlumniAuditStatusEnum {
    APPLY(1, "已申请待审核"),
    REJECT(2, "已驳回"),
    PASS(3, "已通过"),
    EXIT(4, "已退出")
    ;
    private Integer code;
    private String desc;
}
