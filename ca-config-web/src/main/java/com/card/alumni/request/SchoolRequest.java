package com.card.alumni.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author liumingyu
 * @date 2019-12-15 2:11 PM
 */
@Data
@ToString
public class SchoolRequest implements Serializable {

    private Integer id;

    private String name;

    private String address;

    private String email;

    private String tel;

    private String webSite;

    private String desc;

    private String photoImg;
}
