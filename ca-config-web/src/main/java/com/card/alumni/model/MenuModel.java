package com.card.alumni.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author liumingyu
 * @date 2019-12-10 8:02 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "菜单", description = "菜单")
public class MenuModel implements Serializable {

    @ApiModelProperty(value = "菜单ID")
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "菜单类型 1-目录 2-菜单 3-按钮")
    private Integer type;

    @ApiModelProperty(value = "父ID")
    private Integer pid;

    @ApiModelProperty(value = "权限标识")
    private String permission;

    @ApiModelProperty(value = "排序字段")
    private Integer sort;

    @ApiModelProperty(value = "icon")
    private String icon;

    @ApiModelProperty(value = "跳转地址")
    private String path;

    @ApiModelProperty(value = "是否可见")
    private Boolean visible;

    @ApiModelProperty(value = "打开方式 menuItem-页签 menuBlank-新窗口")
    private String target;

    @ApiModelProperty(value = "创建人ID")
    private Integer creator;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人ID")
    private Integer updater;

    @ApiModelProperty(value = "更新实际那")
    private Date updateTime;

    @ApiModelProperty(value = "子菜单列表")
    private List<MenuModel> children;
}
