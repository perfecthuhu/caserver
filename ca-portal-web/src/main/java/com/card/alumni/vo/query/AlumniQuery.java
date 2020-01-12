package com.card.alumni.vo.query;

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
@ApiModel(value = "组织查询", description = "组织查询")
public class AlumniQuery extends PageParam implements Serializable {

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "父级ID")
    private Integer partentId;

    @ApiModelProperty(value = "组织名称ID")
    private String name;

    @ApiModelProperty(value = "组织类型ID")
    private Integer type;

}
