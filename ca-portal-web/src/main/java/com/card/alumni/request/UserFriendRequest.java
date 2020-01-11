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
public class UserFriendRequest implements Serializable {

    @ApiModelProperty(value = "关联ID")
    private Integer id;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "好友ID")
    private Integer friendId;

    public UserFriendRequest() {

    }

    public UserFriendRequest(Integer userId, Integer friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public UserFriendRequest(Integer id, Integer userId, Integer friendId) {
        this.id = id;
        this.userId = userId;
        this.friendId = friendId;
    }
}
