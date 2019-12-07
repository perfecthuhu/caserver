package com.card.alumni.entity;

import java.util.Date;

public class CaAlumniRole {
    private Integer id;

    private Integer alumniId;

    private Integer role;

    private Integer studentId;

    private Date createTime;

    private Date updateTime;

    public CaAlumniRole(Integer id, Integer alumniId, Integer role, Integer studentId, Date createTime, Date updateTime) {
        this.id = id;
        this.alumniId = alumniId;
        this.role = role;
        this.studentId = studentId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public CaAlumniRole() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAlumniId() {
        return alumniId;
    }

    public void setAlumniId(Integer alumniId) {
        this.alumniId = alumniId;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
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