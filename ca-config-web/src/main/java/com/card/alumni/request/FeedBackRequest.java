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
public class FeedBackRequest  extends BaseQueryRequest implements Serializable {

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "状态1，未处理0已处理")
    private Integer status;

    @ApiModelProperty("点击已解决时，使用该字段，其他情况请勿使用（支持批量，传反馈ID）")
    private List<Integer> idList;
}
