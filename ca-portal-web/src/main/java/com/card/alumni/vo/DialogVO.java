package com.card.alumni.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author sunxiaodong10 2020/1/18
 * @date 10:45 AM
 */
@Getter
@Setter
@ApiModel(value = "聊天记录", description = "聊天记录")
public class DialogVO implements Serializable {

    @ApiModelProperty(value = "ID(更新可依据此字段)")
    private Integer id;

    @ApiModelProperty(value = "会话类型1:单聊3:群聊4:聊天室5:客服6:系统7:公众号")
    private Integer type;

    @ApiModelProperty(value = "消息来源：会话列表以此为条件")
    private String fromId;

    @ApiModelProperty(value = "目标id(更新可依据此字段)")
    private String targetId;

    @ApiModelProperty(value = "会话中最后一条消息 Id")
    private String latestMessageId;

    @ApiModelProperty(value = "会话中最后一条消息的消息标识")
    private String objectName;

    @ApiModelProperty(value = "当前会话的未读消息数")
    private Integer unreadMessageCount;

    @ApiModelProperty(value = "会话中最后一条消息,")
    private String latestMessage;

    @ApiModelProperty(value = "发送状态")
    private Integer sentStatus;

    @ApiModelProperty(value = "发送时间")
    private Date sentTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "发送消息用户")
    private UserVO fromUser;

    @ApiModelProperty(value = "接受消息用户")
    private UserVO targetUser;
}
