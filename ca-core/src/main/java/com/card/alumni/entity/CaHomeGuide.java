package com.card.alumni.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CaHomeGuide {
    private Integer id;

    private Integer homePageId;

    private String name;

    private String redirectPath;

    private Integer rank;

    private String backColor;

    private String backImg;

    private Date createTime;

    private Date updateTime;

    private Integer yn;

}