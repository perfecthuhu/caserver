package com.card.alumni.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author liumingyu
 * @date 2019-12-29 4:46 PM
 */
@Data
public class UserModel implements Serializable {

    private Integer id;

    private String phone;

    private String name;

    private String namePy;

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

    private List<Integer> userTagIdList;

    private String pwd;

    private Date pwdLastResetTime;
}
