package com.card.alumni.request;

import com.card.alumni.request.common.BaseQueryRequest;
import lombok.Data;

/**
 * @author liumingyu
 * @date 2019-12-15 6:52 PM
 */
@Data
public class HomeGuideQueryRequest extends BaseQueryRequest {

    private String keyword;

    private Integer homePageId;
}
