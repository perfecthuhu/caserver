package com.card.alumni.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author liumingyu
 * @date 2019-12-15 9:14 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "主页Banner请求", description = "主页Banner请求")
public class HomeBannerRequest implements Serializable {

    @ApiModelProperty(value = "BannerID")
    private Integer id;

    @ApiModelProperty(value = "主页ID")
    private Integer homePageId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "图片地址")
    private String path;

    @ApiModelProperty(value = "排序字段")
    private Integer rank;

    @ApiModelProperty(value = "重定向路径")
    private String redirectPath;
}
