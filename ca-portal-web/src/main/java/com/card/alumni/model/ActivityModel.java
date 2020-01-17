package com.card.alumni.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liumingyu
 * @date 2020-01-16 11:34 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "活动请求", description = "活动请求")
public class ActivityModel implements Serializable {

    @ApiModelProperty(value = "活动ID")
    private Integer id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "子标题")
    private String subTitle;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "活动内容")
    private String content;

    @ApiModelProperty(value = "状态 1:待发布 2:已发布 3:已撤销")
    private Integer status;

    @ApiModelProperty(value = "发布人ID")
    private Integer publisher;

    @ApiModelProperty(value = "发布时间")
    private Date publishTime;

    @ApiModelProperty(value = "创建人ID")
    private Integer creator;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人ID")
    private Integer updater;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
