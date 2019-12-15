package com.card.alumni.request;

import com.card.alumni.request.common.BaseQueryRequest;
import lombok.Data;

/**
 * @author liumingyu
 * @date 2019-12-10 7:59 PM
 */
@Data
public class SchoolQueryRequest extends BaseQueryRequest {

    private String keyword;
}
