package com.card.alumni.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 9:20 PM
 */
@Getter
@Setter
@ApiModel(value = "轮播图", description = "轮播图")
public class HomeGuideVO implements Serializable {

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "所属首页ID")
    private Integer homePageId;

    @ApiModelProperty(value = "标题")
    private String name;

    @ApiModelProperty(value = "跳转链接")
    private String redirectPath;

    @ApiModelProperty(value = "排序")
    private Integer rank;

    @ApiModelProperty(value = "背景色")
    private String backColor;

    @ApiModelProperty(value = "背景图片")
    private String backImg;

}
