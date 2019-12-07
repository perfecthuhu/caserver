package com.card.alumni.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CaUser {
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

}