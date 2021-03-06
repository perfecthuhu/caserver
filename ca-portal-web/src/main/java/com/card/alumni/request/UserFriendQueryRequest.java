package com.card.alumni.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author liumingyu
 * @date 2019-12-21 6:18 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "用户好友查询请求", description = "用户好友查询请求")
public class UserFriendQueryRequest implements Serializable {

    @ApiModelProperty(value = "用户ID 查询我的列表时可不填")
    private Integer userId;

    @ApiModelProperty(value = "当前页数")
    private Integer page;

    @ApiModelProperty(value = "每页大小")
    private Integer size;

    @ApiModelProperty(value = "排序字段")
    private String orderField;

    @ApiModelProperty(value = "排序类型")
    private String orderType;
}
