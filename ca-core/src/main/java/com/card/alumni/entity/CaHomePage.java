package com.card.alumni.entity;

import java.util.Date;

public class CaHomePage {
    private Integer id;

    private String name;

    private Date startTime;

    private Date endTime;

    private Boolean isDelete;

    private Integer updater;

    private Date updateTime;

    private Integer creator;

    private Date createTime;

    public CaHomePage(Integer id, String name, Date startTime, Date endTime, Boolean isDelete, Integer updater, Date updateTime, Integer creator, Date createTime) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isDelete = isDelete;
        this.updater = updater;
        this.updateTime = updateTime;
        this.creator = creator;
        this.createTime = createTime;
    }

    public CaHomePage() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
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
}