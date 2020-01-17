package com.card.alumni.entity;

import java.util.Date;

public class CaDialog {
    private Integer id;

    private Integer type;

    private String fromId;

    private String targetId;

    private String latestMessageId;

    private String objectName;

    private Integer unreadMessageCount;

    private String latestMessage;

    private Integer sentStatus;

    private Date sentTime;

    private String remark;

    private Date createTime;

    private Date updateTime;

    public CaDialog(Integer id, Integer type, String fromId, String targetId, String latestMessageId, String objectName, Integer unreadMessageCount, String latestMessage, Integer sentStatus, Date sentTime, String remark, Date createTime, Date updateTime) {
        this.id = id;
        this.type = type;
        this.fromId = fromId;
        this.targetId = targetId;
        this.latestMessageId = latestMessageId;
        this.objectName = objectName;
        this.unreadMessageCount = unreadMessageCount;
        this.latestMessage = latestMessage;
        this.sentStatus = sentStatus;
        this.sentTime = sentTime;
        this.remark = remark;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public CaDialog() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId == null ? null : fromId.trim();
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId == null ? null : targetId.trim();
    }

    public String getLatestMessageId() {
        return latestMessageId;
    }

    public void setLatestMessageId(String latestMessageId) {
        this.latestMessageId = latestMessageId == null ? null : latestMessageId.trim();
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName == null ? null : objectName.trim();
    }

    public Integer getUnreadMessageCount() {
        return unreadMessageCount;
    }

    public void setUnreadMessageCount(Integer unreadMessageCount) {
        this.unreadMessageCount = unreadMessageCount;
    }

    public String getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(String latestMessage) {
        this.latestMessage = latestMessage == null ? null : latestMessage.trim();
    }

    public Integer getSentStatus() {
        return sentStatus;
    }

    public void setSentStatus(Integer sentStatus) {
        this.sentStatus = sentStatus;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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