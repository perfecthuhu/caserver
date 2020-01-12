package com.card.alumni.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sunxiaodong10 2019/12/19
 * @date 9:19 PM
 */
@Getter
@AllArgsConstructor
public enum  AlumniTypeEnum {
    SCHOOL_ORGANIZATION(1, "学校组织"),
    ALUMNI(2, "校友会"),
    ASSOCIATION(3, "协会")
    ;
    private Integer code;
    private String desc;

}
