package com.card.alumni.entity;

import java.util.Date;

public class CaUserTag {
    private Integer id;

    private Integer studentId;

    private Integer girlFriend;

    private Integer resource;

    private Integer food;

    private Integer job;

    private Date createTime;

    private Date updateTime;

    public CaUserTag(Integer id, Integer studentId, Integer girlFriend, Integer resource, Integer food, Integer job, Date createTime, Date updateTime) {
        this.id = id;
        this.studentId = studentId;
        this.girlFriend = girlFriend;
        this.resource = resource;
        this.food = food;
        this.job = job;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public CaUserTag() {
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

    public Integer getGirlFriend() {
        return girlFriend;
    }

    public void setGirlFriend(Integer girlFriend) {
        this.girlFriend = girlFriend;
    }

    public Integer getResource() {
        return resource;
    }

    public void setResource(Integer resource) {
        this.resource = resource;
    }

    public Integer getFood() {
        return food;
    }

    public void setFood(Integer food) {
        this.food = food;
    }

    public Integer getJob() {
        return job;
    }

    public void setJob(Integer job) {
        this.job = job;
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