package com.card.alumni.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liumingyu
 * @date 2019-12-15 2:11 PM
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(value = "学校", description = "学校")
public class SchoolModel implements Serializable {

    @ApiModelProperty(value = "学校ID")
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "电话")
    private String tel;

    @ApiModelProperty(value = "网站")
    private String webSite;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "图片")
    private String photoImg;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
