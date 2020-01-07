package com.card.alumni.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sunxiaodong10 2019/12/15
 * @date 2:39 PM
 */
@AllArgsConstructor
@Getter
public enum UserTagEnum {

    GIRL_FRIEND(1, "找对象"),
    RESOURCE(2, "找资源"),
    FOOD(3, "吃喝玩乐"),
    JOB(4, "找工作")
    ;

    private Integer code;
    private String desc;

    public static UserTagEnum getUserTagEnum(Integer code) {
        if (code == null) {
            return null;
        }
        for (UserTagEnum userTagEnum : values()) {
            if (userTagEnum.getCode().equals(code)) {
                return userTagEnum;
            }
        }
        return null;
    }
}
