package com.card.alumni.entity;

import java.util.Date;

public class CaAlumniAuditLog {
    private Integer id;

    private Integer studentId;

    private Integer alumniId;

    private Integer auditStatus;

    private Integer auidtorId;

    private Date createTime;

    private Date updateTime;

    public CaAlumniAuditLog(Integer id, Integer studentId, Integer alumniId, Integer auditStatus, Integer auidtorId, Date createTime, Date updateTime) {
        this.id = id;
        this.studentId = studentId;
        this.alumniId = alumniId;
        this.auditStatus = auditStatus;
        this.auidtorId = auidtorId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public CaAlumniAuditLog() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getAlumniId() {
        return alumniId;
    }

    public void setAlumniId(Integer alumniId) {
        this.alumniId = alumniId;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getAuidtorId() {
        return auidtorId;
    }

    public void setAuidtorId(Integer auidtorId) {
        this.auidtorId = auidtorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}