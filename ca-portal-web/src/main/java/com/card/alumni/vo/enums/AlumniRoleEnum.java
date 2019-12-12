package com.card.alumni.vo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sunxiaodong10 2019/12/11
 * @date 8:52 PM
 */
@Getter
@AllArgsConstructor
public enum  AlumniRoleEnum {
    MEMBER(1, "普通成员"),
    ADMIN(2, "管理员"),
    LEADER(3, "会长")
    ;

    private Integer code;
    private String desc;
}
