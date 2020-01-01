package com.card.alumni.request;

import com.card.alumni.request.common.BaseQueryRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liumingyu
 * @date 2019-12-21 6:18 PM
 */
@Data
public class UserFriendQueryRequest extends BaseQueryRequest implements Serializable {

    private Integer userId;
}
