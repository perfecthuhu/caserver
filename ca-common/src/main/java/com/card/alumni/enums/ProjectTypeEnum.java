package com.card.alumni.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 项目类型枚举
 *
 * @author liumingyu
 * @date 2019-12-12 11:40 PM
 */
public enum ProjectTypeEnum {

    /**
     * 普通项目
     */
    ANNOUNCEMENT(1, "项目"),
    ;

    private int code;

    private String desc;

    ProjectTypeEnum(int code, String desc) {
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
        for (ProjectTypeEnum entry : ProjectTypeEnum.values()) {
            if (code == entry.getCode()) {
                return entry.getDesc();
            }
        }
        return StringUtils.EMPTY;
    }

    public static ProjectTypeEnum getEnumByType(Integer code) {
        if (Objects.isNull(code)) {
            return null;
        }
        for (ProjectTypeEnum entry : ProjectTypeEnum.values()) {
            if (code == entry.getCode()) {
                return entry;
            }
        }
        return null;
    }
}
