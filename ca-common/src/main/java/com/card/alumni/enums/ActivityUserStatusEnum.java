package com.card.alumni.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 活动用户报名状态枚举
 *
 * @author liumingyu
 * @date 2019-12-12 11:40 PM
 */
public enum ActivityUserStatusEnum {

    /**
     * 申请中
     */
    WAITING(1, "申请中"),

    /**
     * 已加入
     */
    JOINED(2, "已加入"),

    /**
     * 已退出
     */
    EXITED(3, "已退出");

    private int code;

    private String desc;

    ActivityUserStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static String getDescByCode(Integer code) {
        if (Objects.isNull(code)) {
            return StringUtils.EMPTY;
        }
        for (ActivityUserStatusEnum entry : ActivityUserStatusEnum.values()) {
            if (code == entry.getCode()) {
                return entry.getDesc();
            }
        }
        return StringUtils.EMPTY;
    }

    public static ActivityUserStatusEnum getEnumByType(Integer code) {
        if (Objects.isNull(code)) {
            return null;
        }
        for (ActivityUserStatusEnum entry : ActivityUserStatusEnum.values()) {
            if (code == entry.getCode()) {
                return entry;
            }
        }
        return null;
    }
}
