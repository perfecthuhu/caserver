package com.card.alumni.request;

import com.card.alumni.request.common.BaseQueryRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author sunxiaodong10 2020/2/24
 * @date 10:29 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "查询学校组织校友会成员", description = "查询学校组织校友会成员")
public class UserSchoolAlumniRequest extends BaseQueryRequest implements Serializable {

    @ApiModelProperty(value = "组织ID")
    private Integer alumniId;

    @ApiModelProperty(value = "1,班级2,学院,3,院系,4,校友会")
    private Integer type;

}
