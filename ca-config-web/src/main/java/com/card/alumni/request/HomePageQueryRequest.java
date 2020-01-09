package com.card.alumni.request;

import com.card.alumni.request.common.BaseQueryRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liumingyu
 * @date 2019-12-15 2:05 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "主页查询请求", description = "主页查询请求")
public class HomePageQueryRequest extends BaseQueryRequest {

    @ApiModelProperty(value = "关键字")
    private String keyword;
}
