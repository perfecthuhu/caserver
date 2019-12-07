package com.card.alumni.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CaAlumniAuditLog {
    private Integer id;

    private Integer studentId;

    private Integer alumniId;

    private Integer auditStatus;

    private Integer auidtorId;

    private Date createTime;

    private Date updateTime;

}