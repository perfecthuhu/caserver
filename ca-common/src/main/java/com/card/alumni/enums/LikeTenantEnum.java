package com.card.alumni.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 点赞接入方枚举
 *
 * @author liumingyu
 * @date 2019-12-12 11:40 PM
 */
public enum LikeTenantEnum {

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
    ARTICLE(3, "文章"),

    /**
     * 评论
     */
    COMMENT(4, "评论"),;

    private int code;

    private String desc;

    LikeTenantEnum(int code, String desc) {
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
        for (LikeTenantEnum entry : LikeTenantEnum.values()) {
            if (code == entry.getCode()) {
                return entry.getDesc();
            }
        }
        return StringUtils.EMPTY;
    }

    public static LikeTenantEnum getEnumByType(Integer code) {
        if (Objects.isNull(code)) {
            return null;
        }
        for (LikeTenantEnum entry : LikeTenantEnum.values()) {
            if (code == entry.getCode()) {
                return entry;
            }
        }
        return null;
    }
}
