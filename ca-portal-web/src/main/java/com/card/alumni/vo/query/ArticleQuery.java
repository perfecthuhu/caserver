package com.card.alumni.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 7:54 PM
 */
@Getter
@Setter
@ApiModel(value = "文章查询", description = "文章查询")
public class ArticleQuery extends PageParam {

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "类型(1:新闻 2:公告)")
    private Integer type;

    @ApiModelProperty(value = "发布人")
    private Integer publisher;

    @ApiModelProperty(value = "是否置顶")
    private Boolean hasTop = Boolean.FALSE;

    @ApiModelProperty(value = "发布时间开始")
    private Date publishTimeStart;

    @ApiModelProperty(value = "发布时间结束")
    private Date publishTimeEnd;

}
