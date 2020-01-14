package com.card.alumni.request;

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
@ApiModel(value = "组织管理查询", description = "组织管理查询请求")
public class AlumniAppointAdminRequest {

    @ApiModelProperty(value = "组织ID")
    private Integer alumniId;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

}
