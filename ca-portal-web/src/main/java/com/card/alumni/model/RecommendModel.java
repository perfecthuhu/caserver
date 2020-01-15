package com.card.alumni.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author liumingyu
 * @date 2020-01-01 2:50 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "推荐信息", description = "推荐信息")
public class RecommendModel implements Serializable {

    @ApiModelProperty(value = "推荐ID")
    private Integer recommendId;

    @ApiModelProperty(value = "被推荐人的用户信息")
    private SimpleUserModel user;
}
