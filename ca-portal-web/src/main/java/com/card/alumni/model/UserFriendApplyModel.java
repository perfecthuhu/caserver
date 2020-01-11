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

    @ApiModelProperty(value = "申请ID")
    private Integer id;

    @ApiModelProperty(value = "发起人ID")
    private Integer sponsorId;

    @ApiModelProperty(value = "目标人ID")
    private Integer targetId;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "反馈")
    private String feedback;

    @ApiModelProperty(value = "更新人ID")
    private Integer updater;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "用户信息")
    private SimpleUserModel userModel;
}
