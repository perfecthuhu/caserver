package com.card.alumni.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CaRoleMenuRelation {
    private Integer id;

    private Integer menuId;

    private Integer roleId;

    private Integer creator;

    private Date createTime;

    private Integer updater;

    private Date updateTime;

    private Boolean isDelete;

}