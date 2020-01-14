package com.card.alumni.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 7:47 PM
 */
@Getter
@Setter
@ApiModel(value = "用户查询", description = "用户查询")
public class UserQuery extends PageParam implements Serializable {

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "班级ID")
    private Integer classId;

    @ApiModelProperty(value = "学院ID")
    private Integer collegeId;

    @ApiModelProperty(value = "院系ID")
    private Integer facultyId;

    @ApiModelProperty(value = "隶属校友会ID")
    private Integer alumniId;

    @ApiModelProperty(value = "学校ID")
    private Integer schoolId;

    @ApiModelProperty(value = "用户ID集合")
    private List<Integer> idList;
}
