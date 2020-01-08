package com.card.alumni.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author liumingyu
 * @date 2019-12-10 8:00 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "文章请求(ArticleRequest)", description = "文章请求")
public class ArticleRequest implements Serializable {

    @ApiModelProperty(value = "文章ID")
    private Integer id;

    @ApiModelProperty(value = "标题", required = true)
    private String title;

    @ApiModelProperty(value = "子标题")
    private String subTitle;

    @ApiModelProperty(value = "文章类型", required = true)
    private Integer type;

    @ApiModelProperty(value = "文章内容", required = true)
    private String content;
}
