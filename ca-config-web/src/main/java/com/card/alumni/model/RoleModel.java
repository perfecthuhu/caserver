package com.card.alumni.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liumingyu
 * @date 2019-12-09 9:43 PM
 */
@Data
public class RoleModel implements Serializable {

    private Integer id;

    private String name;

    private String remark;

    private String dataScope;

    private String permission;

    private Integer creator;

    private Date createTime;

    private Integer updater;

    private Date updateTime;
}
