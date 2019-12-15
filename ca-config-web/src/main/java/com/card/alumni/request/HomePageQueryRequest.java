package com.card.alumni.request;

import com.card.alumni.request.common.BaseQueryRequest;
import lombok.Data;

/**
 * @author liumingyu
 * @date 2019-12-15 2:05 PM
 */
@Data
public class HomePageQueryRequest extends BaseQueryRequest {

    private String keyword;
}
