package com.card.alumni.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 活动进度状态枚举
 *
 * @author liumingyu
 * @date 2019-12-12 11:40 PM
 */
public enum ActivityFlowStatusEnum {

    /**
     * 未开始
     */
    WAITING(1, "未开始"),

    /**
     * 进行中
     */
    PROCESSING(2, "进行中"),

    /**
     * 已结束
     */
    ENDED(3, "已结束");

    private int code;

    private String desc;

    ActivityFlowStatusEnum(int code, String desc) {
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
        for (ActivityFlowStatusEnum entry : ActivityFlowStatusEnum.values()) {
            if (code == entry.getCode()) {
                return entry.getDesc();
            }
        }
        return StringUtils.EMPTY;
    }

    public static ActivityFlowStatusEnum getEnumByType(Integer code) {
        if (Objects.isNull(code)) {
            return null;
        }
        for (ActivityFlowStatusEnum entry : ActivityFlowStatusEnum.values()) {
            if (code == entry.getCode()) {
                return entry;
            }
        }
        return null;
    }
}
