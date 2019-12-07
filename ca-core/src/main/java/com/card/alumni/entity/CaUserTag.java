package com.card.alumni.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CaUserTag {
    private Integer id;

    private Integer studentId;

    private Integer girlFriend;

    private Integer resource;

    private Integer food;

    private Integer job;

    private Date createTime;

    private Date updateTime;

}