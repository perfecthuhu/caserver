package com.card.alumni.request;

import com.card.alumni.request.common.BaseQueryRequest;
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
public class FeedBackQueryRequest extends BaseQueryRequest implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "状态1，未处理0已处理")
    private Integer status;

}
