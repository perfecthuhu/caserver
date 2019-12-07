package com.card.alumni.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CaHomePage {
    private Integer id;

    private Integer status;

    private Date startTime;

    private Date endTime;

    private Date createTime;

    private Date updateTime;

    private Integer yn;

}