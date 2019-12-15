package com.card.alumni.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liumingyu
 * @date 2019-12-15 2:11 PM
 */
@Data
@ToString
public class SchoolModel implements Serializable {

    private Integer id;

    private String name;

    private String address;

    private String email;

    private String tel;

    private String webSite;

    private String desc;

    private String photoImg;

    private Date createTime;

    private Date updateTime;
}
