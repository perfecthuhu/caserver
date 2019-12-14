package com.card.alumni.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 菜单打开方式枚举
 *
 * @author liumingyu
 * @date 2019-12-12 11:40 PM
 */
public enum MenuTargetEnum {

    /**
     * 菜单项
     */
    MENU_ITEM("menuItem", "菜单项"),

    /**
     * 新窗口
     */
    MENU_BLANK("menuBlank", "新窗口");

    private String code;

    private String desc;

    MenuTargetEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static String getDescByCode(String code) {
        if (Objects.isNull(code)) {
            return StringUtils.EMPTY;
        }
        for (MenuTargetEnum entry : MenuTargetEnum.values()) {
            if (entry.getCode().equals(code)) {
                return entry.getDesc();
            }
        }
        return StringUtils.EMPTY;
    }

    public static MenuTargetEnum getEnumByType(String code) {
        if (Objects.isNull(code)) {
            return null;
        }
        for (MenuTargetEnum entry : MenuTargetEnum.values()) {
            if (entry.getCode().equals(code)) {
                return entry;
            }
        }
        return null;
    }
}
