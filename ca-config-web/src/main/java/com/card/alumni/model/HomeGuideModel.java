package com.card.alumni.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liumingyu
 * @date 2019-12-15 2:05 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "主页导航", description = "主页导航")
public class HomeGuideModel implements Serializable {

    @ApiModelProperty(value = "导航ID")
    private Integer id;

    @ApiModelProperty(value = "主页ID")
    private Integer homePageId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "跳转地址")
    private String redirectPath;

    @ApiModelProperty(value = "排序字段")
    private Integer rank;

    @ApiModelProperty(value = "背景颜色")
    private String backColor;

    @ApiModelProperty(value = "背景图片")
    private String backImg;

    @ApiModelProperty(value = "创建人ID")
    private Integer creator;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人ID")
    private Integer updater;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
