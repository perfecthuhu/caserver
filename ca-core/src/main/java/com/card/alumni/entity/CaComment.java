package com.card.alumni.entity;

import java.util.Date;

public class CaComment {
    private Long id;

    private Integer tenantId;

    private Long targetId;

    private Long rootId;

    private Integer rootUserId;

    private String rootUserName;

    private String rootUserAvatar;

    private String content;

    private Integer creator;

    private String creatorName;

    private String creatorAvatar;

    private Date createTime;

    private Integer updater;

    private Date updateTime;

    private Boolean isDelete;

    public CaComment(Long id, Integer tenantId, Long targetId, Long rootId, Integer rootUserId, String rootUserName, String rootUserAvatar, String content, Integer creator, String creatorName, String creatorAvatar, Date createTime, Integer updater, Date updateTime, Boolean isDelete) {
        this.id = id;
        this.tenantId = tenantId;
        this.targetId = targetId;
        this.rootId = rootId;
        this.rootUserId = rootUserId;
        this.rootUserName = rootUserName;
        this.rootUserAvatar = rootUserAvatar;
        this.content = content;
        this.creator = creator;
        this.creatorName = creatorName;
        this.creatorAvatar = creatorAvatar;
        this.createTime = createTime;
        this.updater = updater;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
    }

    public CaComment() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Long getRootId() {
        return rootId;
    }

    public void setRootId(Long rootId) {
        this.rootId = rootId;
    }

    public Integer getRootUserId() {
        return rootUserId;
    }

    public void setRootUserId(Integer rootUserId) {
        this.rootUserId = rootUserId;
    }

    public String getRootUserName() {
        return rootUserName;
    }

    public void setRootUserName(String rootUserName) {
        this.rootUserName = rootUserName == null ? null : rootUserName.trim();
    }

    public String getRootUserAvatar() {
        return rootUserAvatar;
    }

    public void setRootUserAvatar(String rootUserAvatar) {
        this.rootUserAvatar = rootUserAvatar == null ? null : rootUserAvatar.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName == null ? null : creatorName.trim();
    }

    public String getCreatorAvatar() {
        return creatorAvatar;
    }

    public void setCreatorAvatar(String creatorAvatar) {
        this.creatorAvatar = creatorAvatar == null ? null : creatorAvatar.trim();
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