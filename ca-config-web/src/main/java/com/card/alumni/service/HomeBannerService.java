package com.card.alumni.service;

import com.card.alumni.entity.CaHomeBanner;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.HomeBannerModel;
import com.card.alumni.request.HomeBannerQueryRequest;
import com.card.alumni.request.HomeBannerRequest;

import java.util.List;

/**
 * @author liumingyu
 * @date 2019-12-15 1:49 PM
 */
public interface HomeBannerService {

    Integer save(HomeBannerRequest request) throws CaConfigException;

    void update(HomeBannerRequest request) throws CaConfigException;

    void deleteById(Integer id) throws CaConfigException;

    void rank(Integer homePageId, List<Integer> children) throws CaConfigException;

    CaHomeBanner findById(Integer id) throws CaConfigException;

    HomeBannerModel findModelById(Integer id) throws CaConfigException;

    List<CaHomeBanner> listRankByHomePageId(Integer homePageId) throws CaConfigException;

    List<HomeBannerModel> listRankModelByHomePageId(Integer homePageId) throws CaConfigException;

    List<HomeBannerModel> listRankModelByRequest(HomeBannerQueryRequest request) throws CaConfigException;
}
