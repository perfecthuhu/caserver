package com.card.alumni.entity;

import java.util.Date;

public class CaUserFriend {
    private Integer id;

    private Integer userId;

    private Integer friendId;

    private Integer creator;

    private Date createTime;

    private Integer updater;

    private Date updateTime;

    private Boolean isDelete;

    public CaUserFriend(Integer id, Integer userId, Integer friendId, Integer creator, Date createTime, Integer updater, Date updateTime, Boolean isDelete) {
        this.id = id;
        this.userId = userId;
        this.friendId = friendId;
        this.creator = creator;
        this.createTime = createTime;
        this.updater = updater;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
    }

    public CaUserFriend() {
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

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}