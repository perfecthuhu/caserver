package com.card.alumni.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CaUserRoleRelation {
    private Integer id;

    private Integer userId;

    private Integer roleId;

    private Integer creator;

    private Date createTime;

    private Integer updater;

    private Date updateTime;

    private Boolean isDelete;

}