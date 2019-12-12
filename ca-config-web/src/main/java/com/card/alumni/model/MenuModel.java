package com.card.alumni.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liumingyu
 * @date 2019-12-10 8:02 PM
 */
@Data
public class MenuModel implements Serializable {

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
}
