package com.card.alumni.request;

import com.card.alumni.request.common.BaseQueryRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author liumingyu
 * @date 2019-12-09 9:44 PM
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "角色查询请求", description = "角色查询请求")
public class RoleQueryRequest extends BaseQueryRequest implements Serializable {

    /**
     * 关键字
     */
    @ApiModelProperty(value = "关键字")
    private String keyword;

}
