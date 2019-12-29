package com.card.alumni.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author liumingyu
 * @date 2019-12-24 11:34 PM
 */
@Data
@ToString
public class UserFriendApplyRequest implements Serializable {

    private Integer id;

    private Integer targetId;

    private String remark;

    private String feedback;
}
