package com.card.alumni.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 11:04 PM
 */
@Getter
@Setter
public class AlumniModel implements Serializable {

    private Integer id;

    private Integer partentId;

    private String name;

    private String description;

    private String rule;

    private String organization;

    private String leaderDesc;

    private String tel;

    private String wx;

    private String qq;

    private String webSite;

    private Integer type;

    private Integer schoolId;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private UserModel leader;

    private List<UserModel> adminVO;

    private List<UserModel> userVOList;

}
