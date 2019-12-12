package com.card.alumni.vo;

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
public class HomeVO implements Serializable {

    private Integer id;

    private Integer status;

    private Date startTime;

    private Date endTime;

    private List<HomeBannerVO> bannerVOList;

    private List<HomeGuideVO> guideVOList;

}
