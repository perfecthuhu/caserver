package com.card.alumni.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author liumingyu
 * @date 2019-12-24 11:34 PM
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(value = "用户好友申请请求", description = "用户好友申请请求")
public class UserFriendApplyRequest implements Serializable {

    @ApiModelProperty(value = "申请ID 发起申请时不需要填写")
    private Integer id;

    @ApiModelProperty(value = "目标用户ID")
    private Integer targetId;

    @ApiModelProperty(value = "备注 发起申请时选填")
    private String remark;

    @ApiModelProperty(value = "反馈 处理申请时选填")
    private String feedback;
}
