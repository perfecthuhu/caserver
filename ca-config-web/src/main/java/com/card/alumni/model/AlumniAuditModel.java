package com.card.alumni.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author sunxiaodong10 2020/2/23
 * @date 2:28 PM
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(value = "组织待审核Model", description = "组织待审核Model")
public class AlumniAuditModel implements Serializable {

    @ApiModelProperty(value = "协会信息")
    private AlumniModel alumniModel;

    @ApiModelProperty(value = "用户信息")
    private UserModel userModel;
}
