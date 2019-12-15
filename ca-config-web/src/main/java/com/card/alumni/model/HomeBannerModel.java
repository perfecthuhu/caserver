package com.card.alumni.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liumingyu
 * @date 2019-12-15 9:16 PM
 */
@Data
public class HomeBannerModel implements Serializable {

    private Integer id;

    private Integer homePageId;

    private String title;

    private String path;

    private Integer rank;

    private String redirectPath;

    private Integer creator;

    private Date createTime;

    private Integer updater;

    private Date updateTime;
}
