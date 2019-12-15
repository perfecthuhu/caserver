package com.card.alumni.service.impl;

import com.card.alumni.constant.CaConstants;
import com.card.alumni.dao.CaHomeBannerMapper;
import com.card.alumni.entity.CaHomeBanner;
import com.card.alumni.entity.CaHomeBannerExample;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.HomeBannerModel;
import com.card.alumni.request.HomeBannerQueryRequest;
import com.card.alumni.request.HomeBannerRequest;
import com.card.alumni.service.HomeBannerService;
import com.card.alumni.utils.RequestUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
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
 * 主页轮播图服务
 *
 * @author liumingyu
 * @date 2019-12-15 1:50 PM
 */
@Service
public class HomeBannerServiceImpl implements HomeBannerService {

    @Autowired
    private CaHomeBannerMapper caHomeBannerMapper;

    @Override
    public Integer save(HomeBannerRequest request) throws CaConfigException {

        checkParam(request);

        CaHomeBanner entity = convert(request);

        Date now = new Date();
        entity.setCreateTime(now);
        entity.setUpdateTime(now);

        Integer userId = RequestUtil.getUserId();
        entity.setCreator(userId);
        entity.setUpdater(userId);

        entity.setIsDelete(Boolean.FALSE);

        if (Objects.isNull(entity.getRank())) {
            int maxRank = caHomeBannerMapper.getMaxRankByHomePageId(request.getHomePageId());
            entity.setRank(maxRank);
        }

        caHomeBannerMapper.insert(entity);

        return entity.getId();
    }

    @Override
    public void update(HomeBannerRequest request) throws CaConfigException {
        checkParam(request);

        if (Objects.isNull(request.getId())) {
            throw new CaConfigException("主键ID不能为空");
        }

        CaHomeBanner entity = convert(request);

        entity.setUpdateTime(new Date());
        entity.setUpdater(RequestUtil.getUserId());

        caHomeBannerMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public void deleteById(Integer id) throws CaConfigException {
        CaHomeBanner entity = findById(id);
        if (Objects.isNull(entity) || entity.getIsDelete()) {
            throw new CaConfigException("当前轮播图不存在或已被删除");
        }

        entity.setUpdateTime(new Date());
        entity.setUpdater(RequestUtil.getUserId());
        entity.setIsDelete(Boolean.TRUE);

        caHomeBannerMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public void rank(Integer homePageId, List<Integer> children) throws CaConfigException {
        if (Objects.isNull(homePageId) || CollectionUtils.isEmpty(children)) {
            return;
        }

        List<CaHomeBanner> bannerList = listRankByHomePageId(homePageId);
        if (CollectionUtils.isEmpty(bannerList)) {
            return;
        }
        if (bannerList.size() != children.size()) {
            throw new CaConfigException("当前数据已过期，请刷新后重试");
        }

        List<CaHomeBanner> sortBannerList = sort(bannerList, children);

        Date now = new Date();
        Integer userId = RequestUtil.getUserId();
        for (int i = 0; i < children.size(); i++) {
            CaHomeBanner guide = sortBannerList.get(i);

            guide.setRank(i + 1);
            guide.setUpdater(userId);
            guide.setUpdateTime(now);
        }

        caHomeBannerMapper.batchUpdate(sortBannerList);
    }

    private List<CaHomeBanner> sort(List<CaHomeBanner> bannerList, List<Integer> children) throws CaConfigException {
        if (CollectionUtils.isEmpty(bannerList) || CollectionUtils.isEmpty(children)) {
            return Lists.newArrayList();
        }

        List<Integer> tempBannerIds = Lists.newArrayList(children);
        for (CaHomeBanner banner : bannerList) {
            if (Objects.isNull(banner)) {
                continue;
            }
            if (!children.contains(banner.getId())) {
                throw new CaConfigException("当前列表数据已过期，请刷新后重试");
            }
            tempBannerIds.remove(banner.getId());
        }

        if (CollectionUtils.isNotEmpty(tempBannerIds)) {
            throw new CaConfigException("当前列表数据已过期，请刷新后重试");
        }

        Map<Integer, CaHomeBanner> bannerMap = bannerList.stream().filter(Objects::nonNull)
                .collect(Collectors.toMap(CaHomeBanner::getId, Function.identity(), (k1, k2) -> k2));

        return children.stream().filter(Objects::nonNull)
                .filter(bannerId -> Objects.nonNull(bannerMap.get(bannerId)))
                .map(bannerMap::get).collect(Collectors.toList());
    }


    @Override
    public CaHomeBanner findById(Integer id) throws CaConfigException {
        if (Objects.isNull(id)) {
            throw new CaConfigException("主键ID不能为空");
        }
        return caHomeBannerMapper.selectByPrimaryKey(id);
    }

    @Override
    public HomeBannerModel findModelById(Integer id) throws CaConfigException {

        CaHomeBanner banner = findById(id);

        return convert2Model(banner);
    }

    @Override
    public List<CaHomeBanner> listRankByHomePageId(Integer homePageId) throws CaConfigException {
        if (Objects.isNull(homePageId)) {
            throw new CaConfigException("主页ID不能为空");
        }

        CaHomeBannerExample example = new CaHomeBannerExample();
        CaHomeBannerExample.Criteria criteria = example.createCriteria();
        criteria.andHomePageIdEqualTo(homePageId);
        criteria.andIsDeleteEqualTo(Boolean.FALSE);

        example.setOrderByClause("rank asc");

        return caHomeBannerMapper.selectByExample(example);
    }

    @Override
    public List<HomeBannerModel> listRankModelByHomePageId(Integer homePageId) throws CaConfigException {

        List<CaHomeBanner> bannerList = listRankByHomePageId(homePageId);

        return convert2ModelList(bannerList);
    }

    @Override
    public List<HomeBannerModel> listRankModelByRequest(HomeBannerQueryRequest request) throws CaConfigException {
        if (Objects.isNull(request)) {
            throw new CaConfigException("查询请求不能为空");
        }

        CaHomeBannerExample example = new CaHomeBannerExample();
        CaHomeBannerExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(Boolean.FALSE);
        if (Objects.nonNull(request.getHomePageId())) {
            criteria.andHomePageIdEqualTo(request.getHomePageId());
        }
        if (StringUtils.isNotBlank(request.getKeyword())) {
            criteria.andTitleLike(CaConstants.LIKE + request.getKeyword() + CaConstants.LIKE);
        }
        example.setOrderByClause("rank asc");
        List<CaHomeBanner> bannerList = caHomeBannerMapper.selectByExample(example);

        return convert2ModelList(bannerList);
    }

    private void checkParam(HomeBannerRequest request) throws CaConfigException {
        if (Objects.isNull(request)) {
            throw new CaConfigException("请求不能为空");
        }
        if (Objects.isNull(request.getHomePageId())) {
            throw new CaConfigException("主页ID不能为空");
        }
        if (StringUtils.isBlank(request.getPath())) {
            throw new CaConfigException("图片地址不能为空");
        }
        if (StringUtils.isBlank(request.getTitle())) {
            throw new CaConfigException("标题不能为空");
        }
    }

    private CaHomeBanner convert(HomeBannerRequest request) {
        CaHomeBanner banner = new CaHomeBanner();
        banner.setId(request.getId());
        banner.setHomePageId(request.getHomePageId());
        banner.setTitle(request.getTitle());
        banner.setPath(request.getPath());
        banner.setRedirectPath(request.getRedirectPath());
        banner.setRank(request.getRank());
        return banner;
    }

    private List<HomeBannerModel> convert2ModelList(List<CaHomeBanner> bannerList) {
        if (CollectionUtils.isEmpty(bannerList)) {
            return Lists.newArrayList();
        }

        return bannerList.stream().filter(Objects::nonNull)
                .map(this::convert2Model).collect(Collectors.toList());
    }

    private HomeBannerModel convert2Model(CaHomeBanner banner) {
        if (Objects.isNull(banner)) {
            return null;
        }

        HomeBannerModel model = new HomeBannerModel();
        model.setId(banner.getId());
        model.setHomePageId(banner.getHomePageId());
        model.setTitle(banner.getTitle());
        model.setPath(banner.getPath());
        model.setRedirectPath(banner.getRedirectPath());
        model.setRank(banner.getRank());
        model.setCreator(banner.getCreator());
        model.setCreateTime(banner.getCreateTime());
        model.setUpdater(banner.getUpdater());
        model.setUpdateTime(banner.getUpdateTime());
        return model;
    }
}
