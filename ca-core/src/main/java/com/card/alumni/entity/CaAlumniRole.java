package com.card.alumni.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CaAlumniRole {
    private Integer id;

    private Integer alumniId;

    private Integer role;

    private Integer studentId;

    private Date createTime;

    private Date updateTime;

}