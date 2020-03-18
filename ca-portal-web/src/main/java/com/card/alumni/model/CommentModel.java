package com.card.alumni.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author liumingyu
 * @date 2020-03-18 11:54 AM
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(value = "评论信息", description = "评论信息")
public class CommentModel implements Serializable {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "接入方类型")
    private Integer tenantId;

    @ApiModelProperty(value = "目标ID")
    private Long targetId;

    @ApiModelProperty(value = "根评论ID")
    private Long rootId;

    @ApiModelProperty(value = "被评论人ID")
    private Integer rootUserId;

    @ApiModelProperty(value = "被评论人名称")
    private String rootUserName;

    @ApiModelProperty(value = "被评论人头像")
    private String rootUserAvatar;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "评论人ID")
    private Integer creator;

    @ApiModelProperty(value = "评论人姓名")
    private String creatorName;

    @ApiModelProperty(value = "评论人头像")
    private String creatorAvatar;

    @ApiModelProperty(value = "评论时间")
    private Date createTime;

    @ApiModelProperty(value = "主键ID")
    private List<CommentModel> children;
}
