package com.card.alumni.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author liumingyu
 * @date 2019-12-15 2:05 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "主页导航请求", description = "主页导航请求")
public class HomeGuideRequest implements Serializable {

    @ApiModelProperty(value = "导航ID")
    private Integer id;

    @ApiModelProperty(value = "主页ID")
    private Integer homePageId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "排序字段")
    private Integer rank;

    @ApiModelProperty(value = "跳转地址")
    private String redirectPath;

    @ApiModelProperty(value = "背景颜色")
    private String backColor;

    @ApiModelProperty(value = "背景图片地址")
    private String backImg;
}
