package com.card.alumni.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liumingyu
 * @date 2019-12-25 11:27 PM
 */
@Data
public class SimpleUserModel implements Serializable {

    private Integer id;

    private String name;

    private Integer sex;

    private String photoImg;
}
