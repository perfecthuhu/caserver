package com.card.alumni.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 9:18 PM
 */
@Getter
@Setter
public class HomeBannerVO implements Serializable {

    private Long id;

    private Integer homePageId;

    private String title;

    private String path;

    private Integer rank;

    private String redirectPath;
}
