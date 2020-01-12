package com.card.alumni.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 9:12 PM
 */
@Getter
@Setter
@ApiModel(value = "学校", description = "学校")
public class SchoolVO implements Serializable {

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "学校名称")
    private String name;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "联系电话")
    private String tel;

    @ApiModelProperty(value = "官方网站")
    private String webSite;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "校徽图片")
    private String photoImg;
}
