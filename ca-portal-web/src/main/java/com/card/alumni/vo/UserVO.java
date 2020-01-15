package com.card.alumni.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 7:45 PM
 */
@Getter
@Setter
@ApiModel(value = "用户VO", description = "用户VO")
public class UserVO implements Serializable {

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "密码")
    @JsonIgnore
    private String pwd;

    @ApiModelProperty(value = "用户名")
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

    @ApiModelProperty(value = "是否毕业1，是0否")
    private Integer status;

    @ApiModelProperty(value = "第二联系人方式")
    private String secondTel;

    @ApiModelProperty(value = "QQ")
    private String qq;

    @ApiModelProperty(value = "微信")
    private String wx;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "现地址")
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
    private List<String> photoLists;

    @ApiModelProperty(value = "用户标签ID")
    private List<Integer> userTagId;

    @ApiModelProperty(value = "反馈")
    private String otherDesc;
}
