package com.card.alumni.request;

import com.card.alumni.request.common.BaseQueryRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liumingyu
 * @date 2019-12-10 7:59 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "菜单查询请求", description = "菜单查询请求")
public class MenuQueryRequest extends BaseQueryRequest {

    @ApiModelProperty(value = "菜单类型 1-目录 2-菜单 3-按钮")
    private Integer type;

    @ApiModelProperty(value = "父ID")
    private Integer pid;

    @ApiModelProperty(value = "关键字")
    private String keyword;
}
