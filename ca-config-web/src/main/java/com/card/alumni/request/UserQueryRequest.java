package com.card.alumni.request;

import com.card.alumni.request.common.BaseQueryRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author liumingyu
 * @date 2019-12-29 4:34 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "用户查询请求", description = "用户查询请求")
public class UserQueryRequest extends BaseQueryRequest implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private Integer id;

    @ApiModelProperty(value = "电话号")
    private String phone;

    @ApiModelProperty(value = "关键字")
    private String keyword;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "是否可用")
    private Integer yn;

    private List<Integer> userIdList;

}
