package com.card.alumni.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 菜单请求
 *
 * @author liumingyu
 * @date 2019-12-10 7:59 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "菜单请求", description = "菜单请求")
public class MenuRequest {

    @ApiModelProperty(value = "菜单ID")
    private Integer id;

    @ApiModelProperty(value = "名称", required = true)
    private String name;

    @ApiModelProperty(value = "菜单类型 1-目录 2-菜单 3-按钮", required = true)
    private Integer type;

    @ApiModelProperty(value = "父ID")
    private Integer pid;

    @ApiModelProperty(value = "权限标识", required = true)
    private String permission;

    @ApiModelProperty(value = "icon")
    private String icon;

    @ApiModelProperty(value = "跳转地址")
    private String path;

    @ApiModelProperty(value = "是否可见 0-不可见 1-可见", required = true)
    private Boolean visible;

    @ApiModelProperty(value = "打开方式 menuItem-页签 menuBlank-新窗口", required = true)
    private String target;
}
