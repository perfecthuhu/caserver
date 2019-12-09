package com.card.alumni.service.impl;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.dao.CaHomeBannerMapper;
import com.card.alumni.dao.CaHomeGuideMapper;
import com.card.alumni.dao.CaHomePageMapper;
import com.card.alumni.dao.CaSchoolMapper;
import com.card.alumni.entity.*;
import com.card.alumni.service.BaseInfoService;
import com.card.alumni.vo.HomeBannerVO;
import com.card.alumni.vo.HomeGuideVO;
import com.card.alumni.vo.HomeVO;
import com.card.alumni.vo.SchoolVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 9:01 PM
 */
@Service
public class BaseInfoServiceImpl implements BaseInfoService {

    @Resource
    private CaSchoolMapper caSchoolMapper;

    @Resource
    private CaHomePageMapper caHomePageMapper;

    @Resource
    private CaHomeBannerMapper caHomeBannerMapper;

    @Resource
    private CaHomeGuideMapper caHomeGuideMapper;

    @Override
    public UnifiedResponse querySchool() {
        CaSchoolExample example = new CaSchoolExample();
        example.createCriteria();
        List<CaSchool> schoolList = caSchoolMapper.selectByExample(example);
        List<SchoolVO> result = null;
        if (CollectionUtils.isNotEmpty(schoolList)) {
            result = schoolList.stream().filter(Objects::nonNull).map(s -> {
                SchoolVO schoolVO = new SchoolVO();
                BeanUtils.copyProperties(s, schoolList);
                return schoolVO;
            }).collect(Collectors.toList());
        }
        return new UnifiedResponse(result);
    }

    @Override
    public UnifiedResponse queryHomeInfo() {
        HomeVO homeVO = new HomeVO();
        Date date = new Date(System.currentTimeMillis());
        CaHomePageExample example = new CaHomePageExample();
        example.createCriteria().andStartTimeLessThan(date)
                .andEndTimeGreaterThanOrEqualTo(date);
        List<CaHomePage> caHomePages = caHomePageMapper.selectByExample(example);
        Integer homeId = caHomePages.get(0).getId();
        CaHomeBannerExample exampleBanner = new CaHomeBannerExample();
        exampleBanner.createCriteria().andHomePageIdEqualTo(homeId);
        List<CaHomeBanner> caHomeBanners = caHomeBannerMapper.selectByExample(exampleBanner);
        CaHomeGuideExample exampleGuide = new CaHomeGuideExample();
        exampleGuide.createCriteria().andHomePageIdEqualTo(homeId);
        List<CaHomeGuide> caHomeGuides = caHomeGuideMapper.selectByExample(exampleGuide);
        homeVO.setBannerVOList(caHomeBanners.stream().map(s -> {
            HomeBannerVO homeBannerVO = new HomeBannerVO();
            BeanUtils.copyProperties(s, homeBannerVO);
            return homeBannerVO;
        }).collect(Collectors.toList()));
        homeVO.setGuideVOList(caHomeGuides.stream().map(s -> {
            HomeGuideVO homeGuideVO = new HomeGuideVO();
            BeanUtils.copyProperties(s, homeGuideVO);
            return homeGuideVO;
        }).collect(Collectors.toList()));
        return new UnifiedResponse(homeVO);
    }
}
