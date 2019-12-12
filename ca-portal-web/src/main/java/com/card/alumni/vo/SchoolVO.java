package com.card.alumni.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 9:12 PM
 */
@Getter
@Setter
public class SchoolVO implements Serializable {

    private Integer id;

    private String name;

    private String address;

    private String email;

    private String tel;

    private String webSite;

    private String desc;

    private String photoImg;
}
