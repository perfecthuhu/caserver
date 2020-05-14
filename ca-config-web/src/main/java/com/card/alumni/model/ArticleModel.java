package com.card.alumni.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liumingyu
 * @date 2019-12-10 8:02 PM
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(value = "文章详情(ArticleModel)", description = "文章详情")
public class ArticleModel implements Serializable {

    @ApiModelProperty(value = "文章ID")
    private Integer id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "子标题")
    private String subTitle;

    @ApiModelProperty(value = "文章类型 1:公告 2:新闻")
    private Integer type;

    @ApiModelProperty(value = "是否发布")
    private Boolean isPublish;

    @ApiModelProperty(value = "是否置顶")
    private Boolean hasTop;

    @ApiModelProperty(value = "权重")
    private Integer weights;

    @ApiModelProperty(value = "发布人")
    private Integer publisher;

    @ApiModelProperty(value = "发布时间")
    private Date publishTime;

    @ApiModelProperty(value = "创建人")
    private Integer creator;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private Integer updater;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "内容")
    private String content;
}
