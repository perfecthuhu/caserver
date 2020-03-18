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
@ApiModel(value = "评论请求", description = "评论请求")
public class CommentRequest implements Serializable {

    @ApiModelProperty(value = "接入方类型 1-活动;2-项目;3-文章", required = true)
    private Integer tenantId;

    @ApiModelProperty(value = "目标ID(具体业务的主键ID)", required = true)
    private Long targetId;

    @ApiModelProperty(value = "根评论ID(第一层评论根ID为0,第二层评论根ID为第一层的评论ID)")
    private Long rootId;

    @ApiModelProperty(value = "评论内容", required = true)
    private String content;

    @ApiModelProperty(value = "被评论人ID(第一层评论可为空,第二层评论需传将要回复人的ID)")
    private Integer rootUserId;

    @ApiModelProperty(value = "被评论人名称(同被评论人ID)")
    private String rootUserName;

    @ApiModelProperty(value = "被评论人头像(同被评论人ID)")
    private String rootUserAvatar;
}
