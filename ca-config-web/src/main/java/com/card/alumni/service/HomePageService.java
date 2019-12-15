package com.card.alumni.service;

import com.card.alumni.common.PageData;
import com.card.alumni.entity.CaHomePage;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.HomePageModel;
import com.card.alumni.request.HomePageQueryRequest;
import com.card.alumni.request.HomePageRequest;

/**
 * @author liumingyu
 * @date 2019-12-15 1:58 PM
 */
public interface HomePageService {

    Integer save(HomePageRequest request) throws CaConfigException;

    void update(HomePageRequest request) throws CaConfigException;

    void deleteById(Integer id) throws CaConfigException;

    CaHomePage findById(Integer id) throws CaConfigException;

    HomePageModel findModelById(Integer id) throws CaConfigException;

    PageData<HomePageModel> pageByRequest(HomePageQueryRequest request) throws CaConfigException;
}
