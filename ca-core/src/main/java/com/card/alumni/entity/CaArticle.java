package com.card.alumni.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
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