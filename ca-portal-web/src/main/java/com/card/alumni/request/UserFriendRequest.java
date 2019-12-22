package com.card.alumni.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liumingyu
 * @date 2019-12-21 6:18 PM
 */
@Data
public class UserFriendRequest implements Serializable {

    private Integer id;

    private Integer userId;

    private Integer friendId;
}