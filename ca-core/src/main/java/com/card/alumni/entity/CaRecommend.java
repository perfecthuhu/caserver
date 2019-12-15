package com.card.alumni.entity;

import java.util.Date;

public class CaRecommend {
    private Integer id;

    private Integer userId;

    private Integer refId;

    private Boolean hasViewed;

    private Date recommendTime;

    public CaRecommend(Integer id, Integer userId, Integer refId, Boolean hasViewed, Date recommendTime) {
        this.id = id;
        this.userId = userId;
        this.refId = refId;
        this.hasViewed = hasViewed;
        this.recommendTime = recommendTime;
    }

    public CaRecommend() {
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

    public Integer getRefId() {
        return refId;
    }

    public void setRefId(Integer refId) {
        this.refId = refId;
    }

    public Boolean getHasViewed() {
        return hasViewed;
    }

    public void setHasViewed(Boolean hasViewed) {
        this.hasViewed = hasViewed;
    }

    public Date getRecommendTime() {
        return recommendTime;
    }

    public void setRecommendTime(Date recommendTime) {
        this.recommendTime = recommendTime;
    }
}