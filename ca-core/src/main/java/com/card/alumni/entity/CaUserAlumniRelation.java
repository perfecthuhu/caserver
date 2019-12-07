package com.card.alumni.entity;

import java.util.Date;

public class CaUserAlumniRelation {
    private Integer id;

    private Integer userId;

    private Integer alumniId;

    private Integer status;

    private Date createTime;

    public CaUserAlumniRelation(Integer id, Integer userId, Integer alumniId, Integer status, Date createTime) {
        this.id = id;
        this.userId = userId;
        this.alumniId = alumniId;
        this.status = status;
        this.createTime = createTime;
    }

    public CaUserAlumniRelation() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAlumniId() {
        return alumniId;
    }

    public void setAlumniId(Integer alumniId) {
        this.alumniId = alumniId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}