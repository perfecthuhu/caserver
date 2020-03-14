package com.card.alumni.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author liumingyu
 * @date 2019-12-10 8:00 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "项目请求", description = "项目请求")
public class ProjectRequest implements Serializable {

    @ApiModelProperty(value = "项目ID")
    private Integer id;

    @ApiModelProperty(value = "标题", required = true)
    private String title;

    @ApiModelProperty(value = "子标题")
    private String subTitle;

    @ApiModelProperty(value = "文章类型 1:普通项目", required = true)
    private Integer type;

    @ApiModelProperty(value = "文章内容", required = true)
    private String content;

    @ApiModelProperty(value = "图片列表")
    private List<String> pictures;
}
