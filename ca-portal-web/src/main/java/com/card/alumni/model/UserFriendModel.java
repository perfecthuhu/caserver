package com.card.alumni.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liumingyu
 * @date 2019-12-21 6:18 PM
 */
@Data
public class UserFriendModel implements Serializable {

    private Integer id;

    private Integer userId;

    private Integer friendId;

    private Integer creator;

    private Date createTime;

    private Integer updater;

    private Date updateTime;
}
