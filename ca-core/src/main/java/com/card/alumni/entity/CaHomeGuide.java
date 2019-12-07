package com.card.alumni.entity;

import java.util.Date;

public class CaHomeGuide {
    private Integer id;

    private Integer homePageId;

    private String name;

    private String redirectPath;

    private Integer rank;

    private String backColor;

    private String backImg;

    private Date createTime;

    private Date updateTime;

    private Integer yn;

    public CaHomeGuide(Integer id, Integer homePageId, String name, String redirectPath, Integer rank, String backColor, String backImg, Date createTime, Date updateTime, Integer yn) {
        this.id = id;
        this.homePageId = homePageId;
        this.name = name;
        this.redirectPath = redirectPath;
        this.rank = rank;
        this.backColor = backColor;
        this.backImg = backImg;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.yn = yn;
    }

    public CaHomeGuide() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHomePageId() {
        return homePageId;
    }

    public void setHomePageId(Integer homePageId) {
        this.homePageId = homePageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRedirectPath() {
        return redirectPath;
    }

    public void setRedirectPath(String redirectPath) {
        this.redirectPath = redirectPath == null ? null : redirectPath.trim();
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getBackColor() {
        return backColor;
    }

    public void setBackColor(String backColor) {
        this.backColor = backColor == null ? null : backColor.trim();
    }

    public String getBackImg() {
        return backImg;
    }

    public void setBackImg(String backImg) {
        this.backImg = backImg == null ? null : backImg.trim();
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

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }
}