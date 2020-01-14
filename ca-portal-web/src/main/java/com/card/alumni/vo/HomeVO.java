package com.card.alumni.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 9:17 PM
 */
@Getter
@Setter
@ApiModel(value = "首页", description = "首页")
public class HomeVO implements Serializable {

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "轮播图模块")
    private List<HomeBannerVO> bannerVOList;

    @ApiModelProperty(value = "导航栏模块")
    private List<HomeGuideVO> guideVOList;

}
