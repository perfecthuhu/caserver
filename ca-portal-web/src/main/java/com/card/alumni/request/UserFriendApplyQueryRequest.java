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
@ApiModel(value = "用户好友申请查询请求", description = "用户好友申请查询请求")
public class UserFriendApplyQueryRequest implements Serializable {

    @ApiModelProperty(value = "申请状态 状态 1-处理中 2-已同意 3-已拒绝")
    private Integer status;

    @ApiModelProperty(value = "当前页数")
    private Integer page;

    @ApiModelProperty(value = "每页大小")
    private Integer size;

    @ApiModelProperty(value = "排序字段 创建时间:create_time 首字母:friend_name_py")
    private String orderField;

    @ApiModelProperty(value = "排序类型 升序:asc 降序:desc")
    private String orderType;
}
