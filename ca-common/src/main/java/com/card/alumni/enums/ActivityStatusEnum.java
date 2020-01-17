package com.card.alumni.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 活动状态枚举
 *
 * @author liumingyu
 * @date 2019-12-12 11:40 PM
 */
public enum ActivityStatusEnum {

    /**
     * 待发布
     */
    WAITING(1, "待发布"),

    /**
     * 已发布
     */
    PUBLISHED(2, "已发布"),

    /**
     * 已撤回
     */
    RETRACTED(3, "已撤回");

    private int code;

    private String desc;

    ActivityStatusEnum(int code, String desc) {
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
        for (ActivityStatusEnum entry : ActivityStatusEnum.values()) {
            if (code == entry.getCode()) {
                return entry.getDesc();
            }
        }
        return StringUtils.EMPTY;
    }

    public static ActivityStatusEnum getEnumByType(Integer code) {
        if (Objects.isNull(code)) {
            return null;
        }
        for (ActivityStatusEnum entry : ActivityStatusEnum.values()) {
            if (code == entry.getCode()) {
                return entry;
            }
        }
        return null;
    }
}
