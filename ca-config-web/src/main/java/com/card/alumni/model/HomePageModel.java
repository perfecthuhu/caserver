package com.card.alumni.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liumingyu
 * @date 2019-12-15 2:05 PM
 */
@Data
public class HomePageModel implements Serializable {

    private Integer id;

    private String name;

    private Date startTime;

    private Date endTime;

    private Integer updater;

    private Date updateTime;

    private Integer creator;

    private Date createTime;
}
