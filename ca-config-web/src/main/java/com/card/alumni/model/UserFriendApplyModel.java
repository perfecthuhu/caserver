package com.card.alumni.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liumingyu
 * @date 2020-01-01 9:11 PM
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

}
