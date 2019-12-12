package com.card.alumni.request;

import lombok.Data;

/**
 * 菜单请求
 *
 * @author liumingyu
 * @date 2019-12-10 7:59 PM
 */
@Data
public class MenuRequest {

    private Integer id;

    private String name;

    private Integer type;

    private Integer pid;

    private String permission;

    private String icon;

    private String path;

    private Boolean visible;

    private String target;
}
