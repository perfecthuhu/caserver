package com.card.alumni.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 11:04 PM
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@ApiModel(value = "组织详情", description = "组织详情")
public class AlumniModel implements Serializable {

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "组织父级ID")
    private Integer partentId;

    @ApiModelProperty(value = "组织名称")
    private String name;

    @ApiModelProperty(value = "组织描述")
    private String description;

    @ApiModelProperty(value = "组织规章制度")
    private String rule;

    @ApiModelProperty(value = "组织结构")
    private String organization;

    @ApiModelProperty(value = "领导简介")
    private String leaderDesc;

    @ApiModelProperty(value = "联系电话")
    private String tel;

    @ApiModelProperty(value = "官方微信")
    private String wx;

    @ApiModelProperty(value = "官方qq")
    private String qq;

    @ApiModelProperty(value = "官方网站")
    private String webSite;

    @ApiModelProperty(value = "协会类型(1:学校组织；2:校友会；3:协会)")
    private Integer type;

    @ApiModelProperty(value = "学校ID")
    private Integer schoolId;

    @ApiModelProperty(value = "状态(0待审核1已通过)")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "会长(只有详情接口会返回该信息)")
    private UserModel leader;

    @ApiModelProperty(value = "管理员(只有详情接口会返回该信息)")
    private List<UserModel> adminVO;

    @ApiModelProperty(value = "成员(只有详情接口会返回该信息)")
    private List<UserModel> userVOList;

}
