package com.card.alumni.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 9:37 PM
 */
@Getter
@Setter
@ApiModel(value = "文章", description = "文章")
public class ArticleModel implements Serializable {

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "副标题")
    private String subTitle;

    @ApiModelProperty(value = "文章类型")
    private Integer type;

    @ApiModelProperty(value = "发布者")
    private Integer publisher;

    @ApiModelProperty(value = "发布时间")
    private Date publishTime;

    @ApiModelProperty(value = "文章正文（html格式）")
    private String content;

    @ApiModelProperty(value = "创建人")
    private Integer creator;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private Integer updater;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
