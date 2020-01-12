package com.card.alumni.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 9:18 PM
 */
@Getter
@Setter
@ApiModel(value = "轮播图", description = "轮播图")
public class HomeBannerVO implements Serializable {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "所属首页ID")
    private Integer homePageId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "图片地址")
    private String path;

    @ApiModelProperty(value = "排序")
    private Integer rank;

    @ApiModelProperty(value = "跳转链接")
    private String redirectPath;
}
