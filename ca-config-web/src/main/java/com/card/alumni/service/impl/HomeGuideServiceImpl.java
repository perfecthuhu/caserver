package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.entity.CaHomePage;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.HomePageModel;
import com.card.alumni.request.HomePageQueryRequest;
import com.card.alumni.request.HomePageRequest;
import com.card.alumni.service.HomeGuideService;
import org.springframework.stereotype.Service;

/**
 * 主页导航服务
 *
 * @author liumingyu
 * @date 2019-12-15 2:00 PM
 */
@Service
public class HomeGuideServiceImpl implements HomeGuideService {
    @Override
    public Integer save(HomePageRequest request) throws CaConfigException {
        return null;
    }

    @Override
    public void update(HomePageRequest request) throws CaConfigException {

    }

    @Override
    public void deleteById(Integer id) throws CaConfigException {

    }

    @Override
    public CaHomePage findById(Integer id) throws CaConfigException {
        return null;
    }

    @Override
    public HomePageModel findModelById(Integer id) throws CaConfigException {
        return null;
    }

    @Override
    public PageData<HomePageModel> pageByRequest(HomePageQueryRequest request) throws CaConfigException {
        return null;
    }
}
