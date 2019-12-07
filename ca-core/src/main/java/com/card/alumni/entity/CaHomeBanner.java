package com.card.alumni.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CaHomeBanner {
    private Long id;

    private Integer homePageId;

    private String title;

    private String path;

    private Integer rank;

    private String redirectPath;

    private Integer creator;

    private Date createTime;

    private Integer updater;

    private Date updateTime;

    private Boolean isDelete;

}