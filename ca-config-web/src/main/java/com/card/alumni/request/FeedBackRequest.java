package com.card.alumni.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author sunxiaodong10 2020/1/21
 * @date 5:43 PM
 */
@Getter
@Setter
@ApiModel(value = "用户反馈", description = "用户反馈")
public class FeedBackRequest implements Serializable {

    @ApiModelProperty(value = "状态 1-未处理;0-已处理")
    private Integer status;

    @ApiModelProperty("反馈ID列表")
    private List<Integer> idList;
}
