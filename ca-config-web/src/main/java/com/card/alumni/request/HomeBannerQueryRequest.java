package com.card.alumni.request;

import com.card.alumni.request.common.BaseQueryRequest;
import lombok.Data;

/**
 * @author liumingyu
 * @date 2019-12-15 9:14 PM
 */
@Data
public class HomeBannerQueryRequest extends BaseQueryRequest {

    private Integer homePageId;

    private String keyword;
}
