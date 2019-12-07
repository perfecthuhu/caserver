package com.card.alumni.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CaArticle {
    private Integer id;

    private String title;

    private String subTitle;

    private Integer type;

    private Boolean isPublish;

    private Integer publisher;

    private Date publishTime;

    private Integer creator;

    private Date createTime;

    private Integer updater;

    private Date updateTime;

    private Boolean isDelete;

    private String content;

}