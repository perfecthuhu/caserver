package com.card.alumni.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Description:
 * User: liuyang
 * Date: 2020-05-14
 * Time: 12:16 上午
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(value = "批量审核", description = "批量审核")
public class BatchCheckModel {
    @ApiModelProperty(value = "审核id列表")
    private List<Integer> idList;

    @ApiModelProperty(value = "状态")
    private Integer auditStatus;
}
