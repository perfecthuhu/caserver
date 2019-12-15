package com.card.alumni.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liumingyu
 * @date 2019-12-15 9:14 PM
 */
@Data
public class HomeBannerRequest implements Serializable {

    private Integer id;

    private Integer homePageId;

    private String title;

    private String path;

    private Integer rank;

    private String redirectPath;
}
