package com.card.alumni.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liumingyu
 * @date 2019-12-10 8:02 PM
 */
@Data
public class ArticleModel implements Serializable {

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

    private String content;
}
