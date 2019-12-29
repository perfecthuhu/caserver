package com.card.alumni.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 好友申请状态枚举
 *
 * @author liumingyu
 * @date 2019-12-12 11:40 PM
 */
public enum FriendApplyStatusEnum {

    /**
     * 等待中
     */
    WAITING(1, "等待中"),

    /**
     * 已同意
     */
    AGREE(2, "已同意"),

    /**
     * 已拒绝
     */
    REFUSE(3, "已拒绝");

    private int code;

    private String desc;

    FriendApplyStatusEnum(int code, String desc) {
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
        for (FriendApplyStatusEnum entry : FriendApplyStatusEnum.values()) {
            if (code == entry.getCode()) {
                return entry.getDesc();
            }
        }
        return StringUtils.EMPTY;
    }

    public static FriendApplyStatusEnum getEnumByType(Integer code) {
        if (Objects.isNull(code)) {
            return null;
        }
        for (FriendApplyStatusEnum entry : FriendApplyStatusEnum.values()) {
            if (code == entry.getCode()) {
                return entry;
            }
        }
        return null;
    }
}
