package com.card.alumni.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CaRole {
    private Integer id;

    private String name;

    private String remark;

    private String dataScope;

    private String permission;

    private Integer creator;

    private Date createTime;

    private Integer updater;

    private Date updateTime;

    private Boolean isDelete;

}