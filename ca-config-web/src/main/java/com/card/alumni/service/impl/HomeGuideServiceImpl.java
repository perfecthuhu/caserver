package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.constant.CaConstants;
import com.card.alumni.dao.CaHomeGuideMapper;
import com.card.alumni.entity.CaHomeGuide;
import com.card.alumni.entity.CaHomeGuideExample;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.HomeGuideModel;
import com.card.alumni.request.HomeGuideQueryRequest;
import com.card.alumni.request.HomeGuideRequest;
import com.card.alumni.service.HomeGuideService;
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
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 主页导航服务
 *
 * @author liumingyu
 * @date 2019-12-15 2:00 PM
 */
@Service
public class HomeGuideServiceImpl implements HomeGuideService {

    @Autowired
    private CaHomeGuideMapper caHomeGuideMapper;

    @Override
    public Integer save(HomeGuideRequest request) throws CaConfigException {

        checkParam(request);

        if (checkExist(request)) {
            throw new CaConfigException("名称不能重复");
        }

        CaHomeGuide entity = convert(request);

        Date now = new Date();
        entity.setCreateTime(now);
        entity.setUpdateTime(now);

        Integer userId = RequestUtil.getUserId();
        entity.setCreator(userId);
        entity.setUpdater(userId);

        entity.setIsDelete(Boolean.FALSE);

        if (Objects.isNull(entity.getRank())) {
            int maxRank = caHomeGuideMapper.getMaxRankByHomePageId(request.getHomePageId());
            entity.setRank(maxRank);
        }

        caHomeGuideMapper.insert(entity);

        return entity.getId();
    }

    @Override
    public void update(HomeGuideRequest request) throws CaConfigException {

        checkParam(request);

        if (Objects.isNull(request.getId())) {
            throw new CaConfigException("主键ID不能为空");
        }

        if (checkExist(request)) {
            throw new CaConfigException("名称不能重复");
        }

        CaHomeGuide entity = convert(request);

        entity.setUpdateTime(new Date());
        entity.setUpdater(RequestUtil.getUserId());

        caHomeGuideMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public void deleteById(Integer id) throws CaConfigException {
        CaHomeGuide guide = findById(id);
        if (Objects.isNull(guide) || guide.getIsDelete()) {
            throw new CaConfigException("当前导航不存在或已被删除");
        }

        guide.setUpdateTime(new Date());
        guide.setUpdater(RequestUtil.getUserId());
        guide.setIsDelete(Boolean.TRUE);

        caHomeGuideMapper.updateByPrimaryKeySelective(guide);
    }

    @Override
    public void rank(Integer homePageId, List<Integer> guideIdList) throws CaConfigException {
        if (Objects.isNull(homePageId) || CollectionUtils.isEmpty(guideIdList)) {
            return;
        }

        List<CaHomeGuide> guideList = listRankByHomePageId(homePageId);

        if (guideList.size() != guideIdList.size()) {
            throw new CaConfigException("当前列表数据已过期，请刷新后重试");
        }

        List<CaHomeGuide> sortGuideList = sort(guideList, guideIdList);

        final Date now = new Date();
        Integer userId = RequestUtil.getUserId();
        for (int i = 0; i < guideIdList.size(); i++) {
            CaHomeGuide guide = sortGuideList.get(i);

            guide.setRank(i + 1);
            guide.setUpdater(userId);
            guide.setUpdateTime(now);
        }

        caHomeGuideMapper.batchUpdate(sortGuideList);
    }

    private List<CaHomeGuide> sort(List<CaHomeGuide> guideList, List<Integer> children) throws CaConfigException {
        if (CollectionUtils.isEmpty(guideList) || CollectionUtils.isEmpty(children)) {
            return Lists.newArrayList();
        }

        List<Integer> tempGuideIds = Lists.newArrayList(children);
        for (CaHomeGuide guide : guideList) {
            if (Objects.isNull(guide)) {
                continue;
            }
            if (!children.contains(guide.getId())) {
                throw new CaConfigException("当前列表数据已过期，请刷新后重试");
            }
            tempGuideIds.remove(guide.getId());
        }

        if (CollectionUtils.isNotEmpty(tempGuideIds)) {
            throw new CaConfigException("当前列表数据已过期，请刷新后重试");
        }

        Map<Integer, CaHomeGuide> guideMap = guideList.stream().filter(Objects::nonNull)
                .collect(Collectors.toMap(CaHomeGuide::getId, Function.identity(), (k1, k2) -> k2));

        return children.stream().filter(Objects::nonNull)
                .filter(guideId -> Objects.nonNull(guideMap.get(guideId)))
                .map(guideMap::get).collect(Collectors.toList());
    }

    @Override
    public CaHomeGuide findById(Integer id) throws CaConfigException {
        if (Objects.isNull(id)) {
            throw new CaConfigException("主键ID不能为空");
        }
        return caHomeGuideMapper.selectByPrimaryKey(id);
    }

    @Override
    public HomeGuideModel findModelById(Integer id) throws CaConfigException {

        CaHomeGuide guide = findById(id);

        return convert2Model(guide);
    }

    @Override
    public List<CaHomeGuide> listRankByHomePageId(Integer homePageId) throws CaConfigException {
        if (Objects.isNull(homePageId)) {
            throw new CaConfigException("主页ID不能为空");
        }

        CaHomeGuideExample example = new CaHomeGuideExample();
        CaHomeGuideExample.Criteria criteria = example.createCriteria();
        criteria.andHomePageIdEqualTo(homePageId);
        criteria.andIsDeleteEqualTo(Boolean.FALSE);

        example.setOrderByClause("rank asc");

        return caHomeGuideMapper.selectByExample(example);
    }

    @Override
    public List<HomeGuideModel> listRankModelByHomePageId(Integer homePageId) throws CaConfigException {

        List<CaHomeGuide> guideList = listRankByHomePageId(homePageId);

        return convert2ModelList(guideList);
    }

    @Override
    public PageData<HomeGuideModel> pageByRequest(HomeGuideQueryRequest request) throws CaConfigException {
        if (Objects.isNull(request)) {
            throw new CaConfigException("请求不能为空");
        }

        int page = Objects.isNull(request.getPage()) ? 1 : request.getPage();
        int size = Objects.isNull(request.getSize()) ? 20 : request.getSize();
        String orderField = StringUtils.isBlank(request.getOrderField()) ? "rank" : request.getOrderField();
        String orderType = StringUtils.isBlank(request.getOrderType()) ? "asc" : request.getOrderType();

        PageHelper.startPage(page, size);

        CaHomeGuideExample example = new CaHomeGuideExample();
        CaHomeGuideExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getHomePageId())) {
            criteria.andHomePageIdEqualTo(request.getHomePageId());
        }
        if (StringUtils.isNotBlank(request.getKeyword())) {
            criteria.andNameLike(CaConstants.LIKE + request.getKeyword() + CaConstants.LIKE);
        }
        criteria.andIsDeleteEqualTo(Boolean.FALSE);
        example.setOrderByClause(orderField + CaConstants.BLANK + orderType);

        List<CaHomeGuide> guideList = caHomeGuideMapper.selectByExample(example);
        PageInfo<CaHomeGuide> pageInfo = new PageInfo<>(guideList);

        return new PageData<>(pageInfo.getTotal(), convert2ModelList(guideList));
    }

    /**
     * 校验当前主页下的名称是否存在
     *
     * @param request 请求
     * @return true-存在 false-不存在
     */
    private boolean checkExist(HomeGuideRequest request) {
        CaHomeGuideExample example = new CaHomeGuideExample();
        CaHomeGuideExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(Boolean.FALSE);
        criteria.andHomePageIdEqualTo(request.getHomePageId());
        criteria.andNameEqualTo(request.getName());
        if (Objects.nonNull(request.getId())) {
            criteria.andIdNotEqualTo(request.getId());
        }
        List<CaHomeGuide> guideList = caHomeGuideMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(guideList);
    }

    private void checkParam(HomeGuideRequest request) throws CaConfigException {
        if (Objects.isNull(request)) {
            throw new CaConfigException("请求不能为空");
        }
        if (Objects.isNull(request.getHomePageId())) {
            throw new CaConfigException("关联主页不能为空");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new CaConfigException("名称不能为空");
        }
    }

    private List<HomeGuideModel> convert2ModelList(List<CaHomeGuide> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Lists.newArrayList();
        }

        return entities.stream().filter(Objects::nonNull)
                .map(this::convert2Model).collect(Collectors.toList());
    }

    private HomeGuideModel convert2Model(CaHomeGuide entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        HomeGuideModel model = new HomeGuideModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setHomePageId(entity.getHomePageId());
        model.setRank(entity.getRank());
        model.setRedirectPath(entity.getRedirectPath());
        model.setBackColor(entity.getBackColor());
        model.setBackImg(entity.getBackImg());
        model.setCreator(entity.getCreator());
        model.setUpdater(entity.getUpdater());
        model.setCreateTime(entity.getCreateTime());
        model.setUpdateTime(entity.getUpdateTime());
        return model;
    }

    private CaHomeGuide convert(HomeGuideRequest request) {
        CaHomeGuide entity = new CaHomeGuide();
        entity.setId(request.getId());
        entity.setName(request.getName());
        entity.setHomePageId(request.getHomePageId());
        entity.setRedirectPath(request.getRedirectPath());
        entity.setBackImg(request.getBackImg());
        entity.setBackColor(request.getBackColor());
        entity.setRank(request.getRank());
        return entity;
    }
}
