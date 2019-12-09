package com.card.alumni.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 9:20 PM
 */
@Getter
@Setter
public class HomeGuideVO implements Serializable {

    private Integer id;

    private Integer homePageId;

    private String name;

    private String redirectPath;

    private Integer rank;

    private String backColor;

    private String backImg;

}
