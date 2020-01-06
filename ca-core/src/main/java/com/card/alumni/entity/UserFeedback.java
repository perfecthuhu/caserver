package com.card.alumni.entity;

import java.util.Date;

public class UserFeedback {
    private Integer id;

    private Integer userId;

    private String feedbackDesc;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    public UserFeedback(Integer id, Integer userId, String feedbackDesc, Integer status, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.feedbackDesc = feedbackDesc;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public UserFeedback() {
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

    public String getFeedbackDesc() {
        return feedbackDesc;
    }

    public void setFeedbackDesc(String feedbackDesc) {
        this.feedbackDesc = feedbackDesc == null ? null : feedbackDesc.trim();
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}