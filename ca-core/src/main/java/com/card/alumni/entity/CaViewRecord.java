package com.card.alumni.entity;

import java.util.Date;

public class CaViewRecord {
    private Integer id;

    private Integer targetId;

    private Integer viewer;

    private String ip;

    private Date createTime;

    public CaViewRecord(Integer id, Integer targetId, Integer viewer, String ip, Date createTime) {
        this.id = id;
        this.targetId = targetId;
        this.viewer = viewer;
        this.ip = ip;
        this.createTime = createTime;
    }

    public CaViewRecord() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Integer getViewer() {
        return viewer;
    }

    public void setViewer(Integer viewer) {
        this.viewer = viewer;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}