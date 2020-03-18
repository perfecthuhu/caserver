package com.card.alumni.request;

import com.card.alumni.request.common.BaseQueryRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author liumingyu
 * @date 2020-03-18 11:13 AM
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(value = "评论查询请求", description = "评论查询请求")
public class CommentQueryRequest extends BaseQueryRequest implements Serializable {

    @ApiModelProperty(value = "接入方类型 1-活动;2-项目;3-文章", required = true)
    private Integer tenantId;

    @ApiModelProperty(value = "目标ID(具体业务的主键ID)", required = true)
    private Long targetId;
}
