package com.card.alumni.entity;

import java.util.Date;

public class CaUserFriendApply {
    private Integer id;

    private Integer sponsorId;

    private Integer targetId;

    private Integer status;

    private String remark;

    private String feedback;

    private Integer updater;

    private Date updateTime;

    private Date createTime;

    public CaUserFriendApply(Integer id, Integer sponsorId, Integer targetId, Integer status, String remark, String feedback, Integer updater, Date updateTime, Date createTime) {
        this.id = id;
        this.sponsorId = sponsorId;
        this.targetId = targetId;
        this.status = status;
        this.remark = remark;
        this.feedback = feedback;
        this.updater = updater;
        this.updateTime = updateTime;
        this.createTime = createTime;
    }

    public CaUserFriendApply() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(Integer sponsorId) {
        this.sponsorId = sponsorId;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback == null ? null : feedback.trim();
    }

    public Integer getUpdater() {
        return updater;
    }

    public void setUpdater(Integer updater) {
        this.updater = updater;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}