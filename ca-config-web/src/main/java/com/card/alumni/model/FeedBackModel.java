package com.card.alumni.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author sunxiaodong10 2020/1/21
 * @date 5:41 PM
 */
@Getter
@Setter
@ApiModel(value = "用户反馈", description = "用户反馈")
public class FeedBackModel implements Serializable {

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "反馈信息")
    private String feedbackDesc;

    @ApiModelProperty(value = "状态1，未处理，0已处理")
    private Integer status;

    @ApiModelProperty(value = "处理人id")
    private Integer handlerId;

    @ApiModelProperty(value = "处理人")
    private String handlerName;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
}
