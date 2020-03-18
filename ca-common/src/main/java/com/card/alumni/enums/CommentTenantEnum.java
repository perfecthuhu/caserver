package com.card.alumni.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 评论接入方枚举
 *
 * @author liumingyu
 * @date 2019-12-12 11:40 PM
 */
public enum CommentTenantEnum {

    /**
     * 活动
     */
    ACTIVITY(1, "活动"),

    /**
     * 项目
     */
    PROJECT(2, "项目"),

    /**
     * 文章
     */
    ARTICLE(3, "文章");

    private int code;

    private String desc;

    CommentTenantEnum(int code, String desc) {
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
        for (CommentTenantEnum entry : CommentTenantEnum.values()) {
            if (code == entry.getCode()) {
                return entry.getDesc();
            }
        }
        return StringUtils.EMPTY;
    }

    public static CommentTenantEnum getEnumByType(Integer code) {
        if (Objects.isNull(code)) {
            return null;
        }
        for (CommentTenantEnum entry : CommentTenantEnum.values()) {
            if (code == entry.getCode()) {
                return entry;
            }
        }
        return null;
    }
}
