package com.card.alumni.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaViewRecord {
    private Integer id;

    private Integer targetId;

    private Integer viewer;

    private String ip;

    private Date createTime;

}