package com.card.alumni.request;

import com.card.alumni.request.common.BaseQueryRequest;
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
public class UserFriendQueryRequest extends BaseQueryRequest implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private Integer userId;
}
