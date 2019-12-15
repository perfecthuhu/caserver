package com.card.alumni.service;

import com.card.alumni.common.PageData;
import com.card.alumni.entity.CaHomeGuide;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.HomeGuideModel;
import com.card.alumni.request.HomeGuideQueryRequest;
import com.card.alumni.request.HomeGuideRequest;

import java.util.List;

/**
 * @author liumingyu
 * @date 2019-12-15 2:00 PM
 */
public interface HomeGuideService {

    Integer save(HomeGuideRequest request) throws CaConfigException;

    void update(HomeGuideRequest request) throws CaConfigException;

    void deleteById(Integer id) throws CaConfigException;

    void rank(Integer homePageId, List<Integer> guideIdList) throws CaConfigException;

    CaHomeGuide findById(Integer id) throws CaConfigException;

    HomeGuideModel findModelById(Integer id) throws CaConfigException;

    List<CaHomeGuide> listRankByHomePageId(Integer homePageId) throws CaConfigException;

    List<HomeGuideModel> listRankModelByHomePageId(Integer homePageId) throws CaConfigException;

    PageData<HomeGuideModel> pageByRequest(HomeGuideQueryRequest request) throws CaConfigException;
}
