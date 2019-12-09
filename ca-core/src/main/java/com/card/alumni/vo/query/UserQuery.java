package com.card.alumni.vo.query;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 7:47 PM
 */
@Getter
@Setter
public class UserQuery extends PageParam implements Serializable {
    private Integer id;

    private String phone;

    private String name;

    private Integer sex;

    private Integer classId;

    private Integer collegeId;

    private Integer facultyId;

    private Integer alumniId;

    private Integer schoolId;
}
