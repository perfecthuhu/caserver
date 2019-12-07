package com.card.alumni.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CaUserAlumniRelation {
    private Integer id;

    private Integer userId;

    private Integer alumniId;

    private Integer status;

    private Date createTime;

}