package com.card.alumni.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liumingyu
 * @date 2019-12-15 2:05 PM
 */
@Data
public class HomeGuideRequest implements Serializable {

    private Integer id;

    private Integer homePageId;

    private String name;

    private String redirectPath;

    private Integer rank;

    private String backColor;

    private String backImg;
}
