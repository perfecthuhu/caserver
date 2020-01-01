package com.card.alumni.request;

import com.card.alumni.request.common.BaseQueryRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liumingyu
 * @date 2020-01-01 8:57 PM
 */
@Data
public class UserFriendApplyQueryRequest extends BaseQueryRequest implements Serializable {

    private Integer sponsorId;

    private Integer targetId;

    private Integer status;
}
