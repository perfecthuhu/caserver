package com.card.alumni.vo;

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
public class AlumniVO implements Serializable {

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

    private Date createTime;

    private Date updateTime;

    private Integer leader_id;

    private List<Integer> admin_id;

    private List<AlumniVO> childrenAlumniVO;
}
