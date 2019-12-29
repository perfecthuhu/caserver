package com.card.alumni.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liumingyu
 * @date 2019-12-25 9:49 PM
 */
@Data
public class UserFriendApplyModel implements Serializable {

    private Integer id;

    private Integer sponsorId;

    private Integer targetId;

    private Integer status;

    private String remark;

    private String feedback;

    private Integer updater;

    private Date updateTime;

    private Date createTime;

    private SimpleUserModel userModel;
}
