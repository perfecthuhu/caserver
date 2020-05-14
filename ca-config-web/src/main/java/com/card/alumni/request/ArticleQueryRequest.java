package com.card.alumni.request;

import com.card.alumni.request.common.BaseQueryRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author liumingyu
 * @date 2019-12-10 8:00 PM
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(value = "文章查询请求", description = "文章查询请求")
public class ArticleQueryRequest extends BaseQueryRequest {

    @ApiModelProperty(value = "关键字")
    private String keyword;

    @ApiModelProperty(value = "文章类型")
    private Integer type;

    @ApiModelProperty(value = "是否发布")
    private Boolean isPublish;

    @ApiModelProperty(value = "是否置顶")
    private Boolean hasTop = Boolean.FALSE;
}
