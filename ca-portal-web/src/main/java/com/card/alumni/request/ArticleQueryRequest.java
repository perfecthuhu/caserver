package com.card.alumni.request;

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
@ApiModel(value = "文章查询", description = "文章查询,如果查置顶没有分页")
public class ArticleQueryRequest {

    @ApiModelProperty(value = "类型(1:新闻 2:公告)")
    private Integer type;

    @ApiModelProperty(value = "关键字")
    private String keyword;

    @ApiModelProperty(value = "发布时间开始")
    private Date publishTimeStart;

    @ApiModelProperty(value = "发布时间结束")
    private Date publishTimeEnd;

    /**
     * 当前页数
     */
    @ApiModelProperty(value = "当前页数")
    private Integer page;

    /**
     * 每页大小
     */
    @ApiModelProperty(value = "每页大小")
    private Integer pageSize;

    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private String orderField;

    /**
     * 排序类型
     */
    @ApiModelProperty(value = "排序类型")
    private String orderType;
}
