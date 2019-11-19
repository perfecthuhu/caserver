package com.card.alumni.context;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户信息
 *
 * @author liumingyu
 * @date 2019-08-18 8:45 PM
 */
@Data
@ToString
public class User implements Serializable {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 性别
     */
    private Integer gender;
}
