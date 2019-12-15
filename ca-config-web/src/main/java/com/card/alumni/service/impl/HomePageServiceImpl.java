package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.constant.CaConstants;
import com.card.alumni.dao.CaHomePageMapper;
import com.card.alumni.entity.CaHomePage;
import com.card.alumni.entity.CaHomePageExample;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.HomePageModel;
import com.card.alumni.request.HomePageQueryRequest;
import com.card.alumni.request.HomePageRequest;
import com.card.alumni.service.HomePageService;
import com.card.alumni.utils.RequestUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 主页服务
 *
 * @author liumingyu
 * @date 2019-12-15 1:59 PM
 */
@Service
public class HomePageServiceImpl implements HomePageService {

    @Autowired
    private CaHomePageMapper caHomePageMapper;

    @Override
    public Integer save(HomePageRequest request) throws CaConfigException {

        checkParam(request);

        CaHomePage entity = convert(request);

        Date now = new Date();
        entity.setCreateTime(now);
        entity.setUpdateTime(now);

        Integer userId = RequestUtil.getUserId();
        entity.setCreator(userId);
        entity.setUpdater(userId);

        entity.setIsDelete(Boolean.FALSE);

        caHomePageMapper.insert(entity);

        return entity.getId();
    }

    @Override
    public void update(HomePageRequest request) throws CaConfigException {
        checkParam(request);

        if (Objects.isNull(request.getId())) {
            throw new CaConfigException("主键ID不能为空");
        }

        CaHomePage entity = convert(request);

        entity.setUpdateTime(new Date());
        entity.setUpdater(RequestUtil.getUserId());

        caHomePageMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public void deleteById(Integer id) throws CaConfigException {
        CaHomePage page = findById(id);
        if (Objects.isNull(page) || page.getIsDelete()) {
            throw new CaConfigException("当前主页不存在或已删除");
        }

        page.setUpdateTime(new Date());
        page.setUpdater(RequestUtil.getUserId());
        page.setIsDelete(Boolean.TRUE);

        caHomePageMapper.updateByPrimaryKeySelective(page);
    }

    @Override
    public CaHomePage findById(Integer id) throws CaConfigException {
        if (Objects.isNull(id)) {
            throw new CaConfigException("主键ID不能为空");
        }
        return caHomePageMapper.selectByPrimaryKey(id);
    }

    @Override
    public HomePageModel findModelById(Integer id) throws CaConfigException {

        CaHomePage page = findById(id);

        return convert2Model(page);
    }

    @Override
    public PageData<HomePageModel> pageByRequest(HomePageQueryRequest request) throws CaConfigException {
        if (Objects.isNull(request)) {
            throw new CaConfigException("查询请求不能为空");
        }

        int page = Objects.isNull(request.getPage()) ? 1 : request.getPage();
        int size = Objects.isNull(request.getSize()) ? 20 : request.getSize();

        PageHelper.startPage(page, size);

        CaHomePageExample example = new CaHomePageExample();
        CaHomePageExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(Boolean.FALSE);
        if (StringUtils.isNotBlank(request.getKeyword())) {
            criteria.andNameLike(CaConstants.LIKE + request.getKeyword() + CaConstants.LIKE);
        }
        List<CaHomePage> pageList = caHomePageMapper.selectByExample(example);
        PageInfo<CaHomePage> pageInfo = new PageInfo<>(pageList);

        return new PageData<>(pageInfo.getTotal(), convert2ModelList(pageList));
    }

    private void checkParam(HomePageRequest request) throws CaConfigException {
        if (Objects.isNull(request)) {
            throw new CaConfigException("请求不能为空");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new CaConfigException("名称不能为空");
        }
        if (Objects.isNull(request.getStartTime())) {
            throw new CaConfigException("开始时间不能为空");
        }
        if (Objects.isNull(request.getEndTime())) {
            throw new CaConfigException("结束时间不能为空");
        }
    }

    private List<HomePageModel> convert2ModelList(List<CaHomePage> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Lists.newArrayList();
        }

        return entities.stream().filter(Objects::nonNull)
                .map(this::convert2Model).collect(Collectors.toList());
    }

    private HomePageModel convert2Model(CaHomePage entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        HomePageModel model = new HomePageModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setStartTime(entity.getStartTime());
        model.setEndTime(entity.getEndTime());
        model.setCreator(entity.getCreator());
        model.setUpdater(entity.getUpdater());
        model.setCreateTime(entity.getCreateTime());
        model.setUpdateTime(entity.getUpdateTime());
        return model;
    }

    private CaHomePage convert(HomePageRequest request) {
        CaHomePage entity = new CaHomePage();
        entity.setId(request.getId());
        entity.setName(request.getName());
        entity.setStartTime(request.getStartTime());
        entity.setEndTime(request.getEndTime());
        return entity;
    }
}
