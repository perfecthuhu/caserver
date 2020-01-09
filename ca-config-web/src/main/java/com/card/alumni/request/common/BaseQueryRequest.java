package com.card.alumni.request.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author liumingyu
 * @date 2019-12-09 9:47 PM
 */
@Data
@ToString
public class BaseQueryRequest implements Serializable {

    /**
     * 当前页数
     */
    @ApiModelProperty(value = "当前页数")
    private Integer page;

    /**
     * 每页大小
     */
    @ApiModelProperty(value = "每页大小")
    private Integer size;

    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private String orderField;

    /**
     * 排序类型
     */
    @ApiModelProperty(value = "排序类型")
    private String orderType;
}
