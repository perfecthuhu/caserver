package com.card.alumni.request;

import com.card.alumni.request.common.BaseQueryRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liumingyu
 * @date 2019-12-10 7:59 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "学校查询请求", description = "学校查询请求")
public class SchoolQueryRequest extends BaseQueryRequest {

    @ApiModelProperty(value = "关键字")
    private String keyword;
}
