package com.card.alumni.request;

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
@ApiModel(value = "点赞请求", description = "点赞请求")
public class LikeRequest implements Serializable {

    @ApiModelProperty(value = "接入方类型 1-活动;2-项目;3-文章;4-评论", required = true)
    private Integer tenantId;

    @ApiModelProperty(value = "目标ID(具体业务的主键ID)", required = true)
    private Long targetId;

}
