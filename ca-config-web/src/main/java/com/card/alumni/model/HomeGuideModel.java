package com.card.alumni.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liumingyu
 * @date 2019-12-15 2:05 PM
 */
@Data
public class HomeGuideModel implements Serializable {

    private Integer id;

    private Integer homePageId;

    private String name;

    private String redirectPath;

    private Integer rank;

    private String backColor;

    private String backImg;

    private Integer creator;

    private Date createTime;

    private Integer updater;

    private Date updateTime;
}
