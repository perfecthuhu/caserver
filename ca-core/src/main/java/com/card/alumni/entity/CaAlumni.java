package com.card.alumni.entity;

import java.util.Date;

public class CaAlumni {
    private Integer id;

    private Integer partentId;

    private String name;

    private String description;

    private String rule;

    private String organization;

    private String leaderDesc;

    private String tel;

    private String wx;

    private String qq;

    private String webSite;

    private Integer type;

    private Integer schoolId;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    public CaAlumni(Integer id, Integer partentId, String name, String description, String rule, String organization, String leaderDesc, String tel, String wx, String qq, String webSite, Integer type, Integer schoolId, Integer status, Date createTime, Date updateTime) {
        this.id = id;
        this.partentId = partentId;
        this.name = name;
        this.description = description;
        this.rule = rule;
        this.organization = organization;
        this.leaderDesc = leaderDesc;
        this.tel = tel;
        this.wx = wx;
        this.qq = qq;
        this.webSite = webSite;
        this.type = type;
        this.schoolId = schoolId;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public CaAlumni() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPartentId() {
        return partentId;
    }

    public void setPartentId(Integer partentId) {
        this.partentId = partentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule == null ? null : rule.trim();
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization == null ? null : organization.trim();
    }

    public String getLeaderDesc() {
        return leaderDesc;
    }

    public void setLeaderDesc(String leaderDesc) {
        this.leaderDesc = leaderDesc == null ? null : leaderDesc.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx == null ? null : wx.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite == null ? null : webSite.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
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