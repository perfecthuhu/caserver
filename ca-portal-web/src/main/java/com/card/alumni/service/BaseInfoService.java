package com.card.alumni.service;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.vo.HomeVO;
import com.card.alumni.vo.SchoolVO;

import java.util.List;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 7:50 PM
 */
public interface BaseInfoService {

    /**
     * 查询所有学校信息
     * @return
     */
    List<SchoolVO> querySchool();

    /**
     * 查询首页结构信息
     * @return
     */
    HomeVO queryHomeInfo();

}
