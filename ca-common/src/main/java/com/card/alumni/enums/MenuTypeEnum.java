package com.card.alumni.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 菜单类型枚举
 *
 * @author liumingyu
 * @date 2019-12-12 11:40 PM
 */
public enum MenuTypeEnum {

    /**
     * 目录
     */
    TABLE(1, "目录"),

    /**
     * 菜单
     */
    MENU(2, "菜单"),

    /**
     * 按钮
     */
    BUTTON(3, "按钮");

    private int code;

    private String desc;

    MenuTypeEnum(int code, String desc) {
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
        for (MenuTypeEnum entry : MenuTypeEnum.values()) {
            if (code == entry.getCode()) {
                return entry.getDesc();
            }
        }
        return StringUtils.EMPTY;
    }

    public static MenuTypeEnum getEnumByType(Integer code) {
        if (Objects.isNull(code)) {
            return null;
        }
        for (MenuTypeEnum entry : MenuTypeEnum.values()) {
            if (code == entry.getCode()) {
                return entry;
            }
        }
        return null;
    }
}
