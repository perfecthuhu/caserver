package com.card.alumni.context;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户信息
 *
 * @author liumingyu
 * @date 2019-08-18 8:45 PM
 */
@Data
@ToString
public class User implements Serializable {

    private Integer id;

    private String phone;

    private String name;

    private String idCard;

    private Integer sex;

    private String professional;

    private Integer education;

    private String acceptAnceDate;

    private String graduateDate;

    private Date birthday;

    private Integer status;

    private String secondTel;

    private String qq;

    private String wx;

    private String email;

    private String address;

    private Integer classId;

    private Integer collegeId;

    private String collegeName;

    private Integer facultyId;

    private String facultyName;

    private String photoImg;

    private String workUnit;

    private String duty;

    private Integer alumniId;

    private String alumniName;

    private String signature;

    private Integer schoolId;

    private String schoolName;

    private Date createTime;

    private Date updateTime;

    private Integer yn;

    private List<Integer> roleIds;
}
