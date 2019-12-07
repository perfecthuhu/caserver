package com.card.alumni.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CaAlumni {
    private Integer id;

    private Integer partentId;

    private String name;

    private String description;

    private String rule;

    private String organization;

    private String leaderDesc;

    private String tel;

    private String wx;

    private String qq;

    private String webSite;

    private Integer type;

    private Date createTime;

    private Date updateTime;

}