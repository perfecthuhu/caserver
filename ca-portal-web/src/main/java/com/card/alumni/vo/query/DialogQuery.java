package com.card.alumni.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author sunxiaodong10 2020/1/18
 * @date 10:49 AM
 */
@Getter
@Setter
@ApiModel(value = "聊天记录", description = "聊天记录")
public class DialogQuery extends PageParam implements Serializable {

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "会话类型1:单聊3:群聊4:聊天室5:客服6:系统7:公众号")
    private Integer type;

    @ApiModelProperty(value = "消息来源：会话列表以此为条件")
    private String fromId;

    @ApiModelProperty(value = "目标id")
    private String targetId;

    @ApiModelProperty(value = "发送状态")
    private Integer sentStatus;

}
