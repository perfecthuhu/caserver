package com.card.alumni.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaUserAlumniRelation {
    private Integer id;

    private Integer userId;

    private Integer alumniId;

    private Integer status;

    private Date createTime;

}