package com.card.alumni.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author liumingyu
 * @date 2019-12-25 11:27 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "简化版用户信息", description = "简化版用户信息")
public class SimpleUserModel implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "首字母")
    private String namePy;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "头像")
    private String photoImg;

    @ApiModelProperty(value = "专业")
    private String professional;

    @ApiModelProperty(value = "学历")
    private Integer education;

    @ApiModelProperty(value = "入学时间")
    private String acceptAnceDate;

    @ApiModelProperty(value = "毕业时间")
    private String graduateDate;

    @ApiModelProperty(value = "现住址")
    private String address;

    @ApiModelProperty(value = "班级ID")
    private Integer classId;

    @ApiModelProperty(value = "学院ID")
    private Integer collegeId;

    @ApiModelProperty(value = "学院名称")
    private String collegeName;

    @ApiModelProperty(value = "院系ID")
    private Integer facultyId;

    @ApiModelProperty(value = "院系名称")
    private String facultyName;

    @ApiModelProperty(value = "工作单位")
    private String workUnit;

    @ApiModelProperty(value = "职务")
    private String duty;

    @ApiModelProperty(value = "隶属校友会ID")
    private Integer alumniId;

    @ApiModelProperty(value = "隶属校友会名称")
    private String alumniName;

    @ApiModelProperty(value = "个性签名")
    private String signature;

    @ApiModelProperty(value = "学校ID")
    private Integer schoolId;

    @ApiModelProperty(value = "学校名称")
    private String schoolName;

    @ApiModelProperty(value = "个人风采")
    private List<String> photoList;
}
