package com.card.alumni.request;

import com.card.alumni.request.common.BaseQueryRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author liumingyu
 * @date 2020-01-16 11:35 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "活动查询请求", description = "活动查询请求")
public class ActivityQueryRequest extends BaseQueryRequest implements Serializable {

    @ApiModelProperty(value = "关键字")
    private String keyword;

    @ApiModelProperty(value = "状态 1:待发布 2:已发布 3:已撤销")
    private Integer status;

    @ApiModelProperty(value = "进度 1:未开始 2:进行中 3:已结束")
    private Integer flowStatus;

}
