package com.card.alumni.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author sunxiaodong10 2020/1/21
 * @date 5:28 PM
 */
@Getter
@Setter
@ApiModel(value = "用户反馈", description = "用户反馈")
public class FeedbackRequest implements Serializable {

    @ApiModelProperty("用户反馈")
    private String feedBackDesc;
}
