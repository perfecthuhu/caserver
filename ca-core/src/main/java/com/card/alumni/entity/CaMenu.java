package com.card.alumni.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaMenu {
    private Integer id;

    private String name;

    private Integer type;

    private Integer pid;

    private String permission;

    private Integer sort;

    private String icon;

    private String path;

    private Boolean visible;

    private String target;

    private Integer creator;

    private Date createTime;

    private Integer updater;

    private Date updateTime;

    private Boolean isDelete;

}