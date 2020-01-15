package com.card.alumni.request;

import com.card.alumni.request.common.BaseQueryRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author liumingyu
 * @date 2020-01-01 8:57 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "用户好友申请查询请求", description = "用户好友申请查询请求")
public class UserFriendApplyQueryRequest extends BaseQueryRequest implements Serializable {

    @ApiModelProperty(value = "发起人ID")
    private Integer sponsorId;

    @ApiModelProperty(value = "目标人ID")
    private Integer targetId;

    @ApiModelProperty(value = "状态 1-处理中 2-已同意 3-已拒绝")
    private Integer status;
}
