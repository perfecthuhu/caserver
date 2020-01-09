package com.card.alumni.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author liumingyu
 * @date 2019-12-09 9:38 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "角色请求", description = "角色请求")
public class RoleRequest implements Serializable {

    @ApiModelProperty(value = "角色ID")
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "数据范围")
    private String dataScope;

    @ApiModelProperty(value = "权限标识")
    private String permission;
}
