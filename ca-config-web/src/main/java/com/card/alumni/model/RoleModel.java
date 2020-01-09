package com.card.alumni.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liumingyu
 * @date 2019-12-09 9:43 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "角色", description = "角色")
public class RoleModel implements Serializable {

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

    @ApiModelProperty(value = "创建人ID")
    private Integer creator;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人ID")
    private Integer updater;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
