package com.card.alumni.request;

import com.card.alumni.request.common.BaseQueryRequest;
import lombok.Data;

/**
 * @author liumingyu
 * @date 2020-02-22 9:42 PM
 */
@Data
public class ActivityUserQueryRequest extends BaseQueryRequest {

    private Integer status;

}
