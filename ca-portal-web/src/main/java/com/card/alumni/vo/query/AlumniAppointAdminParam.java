package com.card.alumni.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author sunxiaodong10 2019/12/21
 * @date 1:30 PM
 */
@Getter
@Setter
@ApiModel(value = "组织查询", description = "组织查询")
public class AlumniAppointAdminParam {

    @ApiModelProperty(value = "协会ID")
    private Integer alumniId;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;
}
