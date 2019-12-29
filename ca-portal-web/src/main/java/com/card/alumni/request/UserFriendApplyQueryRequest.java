package com.card.alumni.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liumingyu
 * @date 2019-12-21 6:18 PM
 */
@Data
public class UserFriendApplyQueryRequest implements Serializable {

    private Integer status;

    private Integer page;

    private Integer size;

    private String orderField;

    private String orderType;
}
