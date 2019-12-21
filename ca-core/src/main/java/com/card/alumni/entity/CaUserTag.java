package com.card.alumni.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaUserTag {
    private Integer id;

    private Integer userId;

    private Integer girlFriend;

    private Integer resource;

    private Integer food;

    private Integer job;

    private Date createTime;

    private Date updateTime;

}