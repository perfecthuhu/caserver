package com.card.alumni.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liumingyu
 * @date 2019-12-25 11:27 PM
 */
@Data
public class SimpleUserModel implements Serializable {

    private Integer id;

    private String name;

    private String namePy;

    private Integer sex;

    private String photoImg;

    private String professional;

    private Integer education;

    private String acceptAnceDate;

    private String graduateDate;

    private String address;

    private Integer classId;

    private Integer collegeId;

    private String collegeName;

    private Integer facultyId;

    private String facultyName;

    private String workUnit;

    private String duty;

    private Integer alumniId;

    private String alumniName;

    private String signature;

    private Integer schoolId;

    private String schoolName;
}
