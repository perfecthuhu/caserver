package com.card.alumni.request;

import com.card.alumni.request.common.BaseQueryRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author liumingyu
 * @date 2019-12-29 4:34 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "用户查询请求", description = "用户查询请求")
public class UserQueryRequest extends BaseQueryRequest implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private Integer id;

    @ApiModelProperty(value = "电话号")
    private String phone;

    @ApiModelProperty(value = "关键字")
    private String keyword;

    @ApiModelProperty(value = "状态 待定字段")
    private Integer status;

    @ApiModelProperty(value = "是否可用 1:可用 2:不可用")
    private Integer yn;

    @ApiModelProperty(value = "用户ID集合")
    private List<Integer> userIdList;

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

}
