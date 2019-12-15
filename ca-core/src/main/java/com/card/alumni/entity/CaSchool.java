package com.card.alumni.entity;

import java.util.Date;

public class CaSchool {
    private Integer id;

    private String name;

    private String address;

    private String email;

    private String tel;

    private String webSite;

    private String desc;

    private String photoImg;

    private Integer updater;

    private Date updateTime;

    private Integer creator;

    private Date createTime;

    private Boolean isDelete;

    public CaSchool(Integer id, String name, String address, String email, String tel, String webSite, String desc, String photoImg, Integer updater, Date updateTime, Integer creator, Date createTime, Boolean isDelete) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.tel = tel;
        this.webSite = webSite;
        this.desc = desc;
        this.photoImg = photoImg;
        this.updater = updater;
        this.updateTime = updateTime;
        this.creator = creator;
        this.createTime = createTime;
        this.isDelete = isDelete;
    }

    public CaSchool() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite == null ? null : webSite.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public String getPhotoImg() {
        return photoImg;
    }

    public void setPhotoImg(String photoImg) {
        this.photoImg = photoImg == null ? null : photoImg.trim();
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

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}