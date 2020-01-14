package com.card.alumni.vo;

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
public class ArticleVO implements Serializable {

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
}
