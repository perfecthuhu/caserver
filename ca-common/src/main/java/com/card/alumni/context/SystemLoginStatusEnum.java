package com.card.alumni.context;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sunxiaodong10 2019/12/7
 * @date 2:42 PM
 */
@Getter
@AllArgsConstructor
public enum SystemLoginStatusEnum {
    /**
     * 未登录
     */
    NO_LOGIN(0, "未登录"),
    /**
     * 已资质认证通过并登录
     */
    NOT_PASS(1, "已登录，未审核通过"),
    /**
     * 已资质认证通过并登录
     */
    PASS(2, "已登录，已审核通过"),
    ;
    /**
     * 状态值
     */
    private int status;
    /**
     * 状态描述
     */
    private String desc;
}
