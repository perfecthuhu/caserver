package com.card.alumni.request;

import com.card.alumni.request.common.BaseQueryRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 11:07 PM
 */
@Getter
@Setter
@ApiModel(value = "组织查询", description = "组织查询请求")
public class AlumniRequest extends BaseQueryRequest implements Serializable {

    @ApiModelProperty(value = "组织ID")
    private Integer id;

    @ApiModelProperty(value = "组织父级ID（根据父级id查询子级）")
    private Integer partentId;

    @ApiModelProperty(value = "组织名称")
    private String name;

    @ApiModelProperty(value = "组织类型（1：学校组织， 2：校友会，3：协会）")
    private Integer type;

    @ApiModelProperty(value = "审核状态，1：待审核，2：已驳回，3：已通过，4：已退出")
    private Integer auditStatus;

}
