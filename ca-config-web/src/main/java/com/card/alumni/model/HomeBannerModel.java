package com.card.alumni.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liumingyu
 * @date 2019-12-15 9:16 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "主页Banner", description = "主页Banner")
public class HomeBannerModel implements Serializable {

    @ApiModelProperty(value = "BannerId")
    private Integer id;

    @ApiModelProperty(value = "主页ID")
    private Integer homePageId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "图片地址")
    private String path;

    @ApiModelProperty(value = "排序字段")
    private Integer rank;

    @ApiModelProperty(value = "跳转地址")
    private String redirectPath;

    @ApiModelProperty(value = "创建人ID")
    private Integer creator;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人ID")
    private Integer updater;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
