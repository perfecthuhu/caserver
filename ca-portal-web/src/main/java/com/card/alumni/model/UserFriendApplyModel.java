package com.card.alumni.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liumingyu
 * @date 2019-12-25 9:49 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "用户的好友申请", description = "用户的好友申请")
public class UserFriendApplyModel implements Serializable {

    @ApiModelProperty(value = "申请ID 处理申请时需要")
    private Integer id;

    @ApiModelProperty(value = "发起人ID")
    private Integer sponsorId;

    @ApiModelProperty(value = "目标人ID")
    private Integer targetId;

    @ApiModelProperty(value = "状态 1-处理中 2-已同意 3-已拒绝")
    private Integer status;

    @ApiModelProperty(value = "备注 发起申请时选填")
    private String remark;

    @ApiModelProperty(value = "反馈 处理申请时选填")
    private String feedback;

    @ApiModelProperty(value = "更新人ID")
    private Integer updater;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "用户信息 1.查询申请列表时为目标人列表 2.查询处理列表时为发起人列表")
    private SimpleUserModel userModel;
}
