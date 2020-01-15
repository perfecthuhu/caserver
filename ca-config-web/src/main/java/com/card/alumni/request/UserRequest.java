package com.card.alumni.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author liumingyu
 * @date 2019-12-29 4:34 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "用户请求", description = "用户请求")
public class UserRequest implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private Integer id;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "密码")
    private String pwd;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "身份证")
    private String idCard;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "专业")
    private String professional;

    @ApiModelProperty(value = "学历")
    private Integer education;

    @ApiModelProperty(value = "入学时间")
    private String acceptAnceDate;

    @ApiModelProperty(value = "毕业时间")
    private String graduateDate;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "第二联系方式")
    private String secondTel;

    @ApiModelProperty(value = "QQ号")
    private String qq;

    @ApiModelProperty(value = "微信号")
    private String wx;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "现地址")
    private String address;

    @ApiModelProperty(value = "班级ID")
    private Integer classId;

    @ApiModelProperty(value = "班级名称")
    private String className;

    @ApiModelProperty(value = "学院ID")
    private Integer collegeId;

    @ApiModelProperty(value = "学院名称")
    private String collegeName;

    @ApiModelProperty(value = "院系ID")
    private Integer facultyId;

    @ApiModelProperty(value = "院系名称")
    private String facultyName;

    @ApiModelProperty(value = "头像地址")
    private String photoImg;

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
    private String photoList;

    @ApiModelProperty(value = "用户标签列表")
    private List<Integer> userTagIdList;

}
