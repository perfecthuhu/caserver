package com.card.alumni.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaAlumniAuditLog {
    private Integer id;

    private Integer studentId;

    private Integer alumniId;

    private Integer auditStatus;

    private Integer auidtorId;

    private Date createTime;

    private Date updateTime;

}