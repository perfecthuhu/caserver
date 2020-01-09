package com.card.alumni.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author liumingyu
 * @date 2019-12-15 2:11 PM
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(value = "学校请求", description = "学校请求")
public class SchoolRequest implements Serializable {

    @ApiModelProperty(value = "学校ID")
    private Integer id;

    @ApiModelProperty(value = "学校名称")
    private String name;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "电话")
    private String tel;

    @ApiModelProperty(value = "网站地址")
    private String webSite;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "图片")
    private String photoImg;
}
