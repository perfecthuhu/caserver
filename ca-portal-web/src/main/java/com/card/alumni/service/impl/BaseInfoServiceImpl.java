package com.card.alumni.service.impl;

import com.card.alumni.dao.CaHomeBannerMapper;
import com.card.alumni.dao.CaHomeGuideMapper;
import com.card.alumni.dao.CaHomePageMapper;
import com.card.alumni.dao.CaSchoolMapper;
import com.card.alumni.entity.CaHomeBanner;
import com.card.alumni.entity.CaHomeBannerExample;
import com.card.alumni.entity.CaHomeGuide;
import com.card.alumni.entity.CaHomeGuideExample;
import com.card.alumni.entity.CaHomePage;
import com.card.alumni.entity.CaHomePageExample;
import com.card.alumni.entity.CaSchool;
import com.card.alumni.entity.CaSchoolExample;
import com.card.alumni.exception.CaException;
import com.card.alumni.service.BaseInfoService;
import com.card.alumni.vo.HomeBannerVO;
import com.card.alumni.vo.HomeGuideVO;
import com.card.alumni.vo.HomeVO;
import com.card.alumni.vo.SchoolVO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
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

    private final Logger LOGGER = LoggerFactory.getLogger(BaseInfoServiceImpl.class);

    @Resource
    private CaSchoolMapper caSchoolMapper;

    @Resource
    private CaHomePageMapper caHomePageMapper;

    @Resource
    private CaHomeBannerMapper caHomeBannerMapper;

    @Resource
    private CaHomeGuideMapper caHomeGuideMapper;

    @Override
    public List<SchoolVO> querySchool() {
        CaSchoolExample example = new CaSchoolExample();
        example.createCriteria();
        List<CaSchool> schoolList = caSchoolMapper.selectByExample(example);
        List<SchoolVO> result = null;
        if (CollectionUtils.isNotEmpty(schoolList)) {
            result = schoolList.stream().filter(Objects::nonNull).map(s -> {
                SchoolVO schoolVO = convert2SchoolVO(s);
                return schoolVO;
            }).collect(Collectors.toList());
        }
        return result;
    }

    public static SchoolVO convert2SchoolVO(CaSchool caSchool) {
        if (caSchool == null) {
            return null;
        }
        SchoolVO schoolVO = new SchoolVO();
        schoolVO.setId(caSchool.getId());
        schoolVO.setName(caSchool.getName());
        schoolVO.setAddress(caSchool.getAddress());
        schoolVO.setEmail(caSchool.getEmail());
        schoolVO.setTel(caSchool.getTel());
        schoolVO.setWebSite(caSchool.getWebSite());
        schoolVO.setDesc(caSchool.getDesc());
        schoolVO.setPhotoImg(caSchool.getPhotoImg());
        return schoolVO;
    }

    @Override
    public HomeVO queryHomeInfo() {
        HomeVO homeVO = new HomeVO();
        Date date = new Date(System.currentTimeMillis());
        CaHomePageExample example = new CaHomePageExample();
        example.createCriteria().andStartTimeLessThan(date)
                .andEndTimeGreaterThanOrEqualTo(date).andIsDeleteEqualTo(Boolean.FALSE);
        List<CaHomePage> caHomePages = caHomePageMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(caHomePages)) {
            throw new CaException("首页未配置");
        }
        Integer homeId = caHomePages.get(0).getId();
        CaHomeBannerExample exampleBanner = new CaHomeBannerExample();
        exampleBanner.createCriteria().andHomePageIdEqualTo(homeId).andIsDeleteEqualTo(Boolean.FALSE);
        List<CaHomeBanner> caHomeBanners = caHomeBannerMapper.selectByExample(exampleBanner);
        CaHomeGuideExample exampleGuide = new CaHomeGuideExample();
        exampleGuide.createCriteria().andHomePageIdEqualTo(homeId).andIsDeleteEqualTo(Boolean.FALSE);
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
        return homeVO;
    }

    @Override
    public String uploadFile(MultipartFile file, Long maxSize) throws CaException {
        if (file.isEmpty()) {
            throw new CaException("请重新选择文件，文件为空");
        }
        if (maxSize == null || maxSize <= 0) {
            maxSize = 2 * 1024 * 1024L;
        }
        if (file.getSize() > maxSize) {
            throw new CaException("图片上传失败，文件过大，请重新选择！");
        }
        String fileName = file.getOriginalFilename();
        String filePath = "/home/work/servers/tomcat8/webapps/";
        String path = filePath + fileName;
        File dest = new File(path);
        try {
            file.transferTo(dest);
            LOGGER.info("上传成功");
            return "上传成功";
        } catch (IOException e) {
            LOGGER.error("文件上传失败", e);
        }
        return "47.105.149.158:8080/" + path;
    }

}
