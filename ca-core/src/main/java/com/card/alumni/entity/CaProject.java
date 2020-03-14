package com.card.alumni.entity;

import java.util.Date;

public class CaProject {
    private Integer id;

    private String title;

    private String subTitle;

    private Integer type;

    private Boolean isPublish;

    private Integer publisher;

    private Date publishTime;

    private Long viewCount;

    private String picture;

    private Integer creator;

    private Date createTime;

    private Integer updater;

    private Date updateTime;

    private Boolean isDelete;

    private String content;

    public CaProject(Integer id, String title, String subTitle, Integer type, Boolean isPublish, Integer publisher, Date publishTime, Long viewCount, String picture, Integer creator, Date createTime, Integer updater, Date updateTime, Boolean isDelete, String content) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.type = type;
        this.isPublish = isPublish;
        this.publisher = publisher;
        this.publishTime = publishTime;
        this.viewCount = viewCount;
        this.picture = picture;
        this.creator = creator;
        this.createTime = createTime;
        this.updater = updater;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
        this.content = content;
    }

    public CaProject() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle == null ? null : subTitle.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(Boolean isPublish) {
        this.isPublish = isPublish;
    }

    public Integer getPublisher() {
        return publisher;
    }

    public void setPublisher(Integer publisher) {
        this.publisher = publisher;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}