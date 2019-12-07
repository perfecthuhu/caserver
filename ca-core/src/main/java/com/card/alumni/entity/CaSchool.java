package com.card.alumni.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CaSchool {
    private Integer id;

    private String name;

    private String address;

    private String email;

    private String tel;

    private String webSite;

    private String desc;

    private String photoImg;

    private Date createTime;

    private Date updateTime;

}