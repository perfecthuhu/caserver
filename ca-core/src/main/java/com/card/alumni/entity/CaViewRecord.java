package com.card.alumni.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CaViewRecord {
    private Integer id;

    private Integer targetId;

    private Integer viewer;

    private String ip;

    private Date createTime;

}