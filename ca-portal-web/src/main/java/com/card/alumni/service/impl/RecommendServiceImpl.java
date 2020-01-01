package com.card.alumni.service.impl;

import com.card.alumni.dao.CaRecommendMapper;
import com.card.alumni.entity.CaRecommend;
import com.card.alumni.entity.CaRecommendExample;
import com.card.alumni.entity.CaUser;
import com.card.alumni.exception.CaException;
import com.card.alumni.model.RecommendModel;
import com.card.alumni.model.SimpleUserModel;
import com.card.alumni.service.RecommendService;
import com.card.alumni.service.UserFriendService;
import com.card.alumni.service.UserLocalService;
import com.card.alumni.utils.DateUtils;
import com.card.alumni.utils.RequestUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 用户推荐服务
 *
 * @author liumingyu
 * @date 2019-12-21 1:08 AM
 */
@Service
public class RecommendServiceImpl implements RecommendService {

    @Autowired
    private CaRecommendMapper caRecommendMapper;

    @Autowired
    private UserLocalService userLocalService;

    @Autowired
    private UserFriendService userFriendService;

    @Override
    public Integer save(CaRecommend recommend) throws CaException {

        checkParam(recommend);

        recommend.setHasViewed(Boolean.FALSE);
        recommend.setRecommendTime(new Date());

        caRecommendMapper.insert(recommend);

        return recommend.getId();
    }

    @Override
    public void batchSave(List<CaRecommend> recommendList) throws CaException {
        if (CollectionUtils.isEmpty(recommendList)) {
            return;
        }

        Date now = new Date();
        for (CaRecommend recommend : recommendList) {
            checkParam(recommend);

            recommend.setRecommendTime(now);
            recommend.setHasViewed(Boolean.FALSE);
        }

        caRecommendMapper.batchInsert(recommendList);
    }

    @Override
    public void batchSave(Integer userId, List<Integer> refIdList) throws CaException {
        if (Objects.isNull(userId) || CollectionUtils.isEmpty(refIdList)) {
            return;
        }

        List<CaRecommend> recommendList = Lists.newArrayListWithCapacity(refIdList.size());
        refIdList.forEach(refId -> {
            CaRecommend recommend = new CaRecommend();
            recommend.setUserId(userId);
            recommend.setRefId(refId);
            recommendList.add(recommend);
        });

        batchSave(recommendList);
    }

    @Override
    public void update(CaRecommend recommend) throws CaException {
        if (Objects.isNull(recommend.getId())) {
            throw new CaException("推荐ID不能为空");
        }

        caRecommendMapper.updateByPrimaryKeySelective(recommend);
    }

    @Override
    public void browse(Integer id) throws CaException {
        CaRecommend recommend = findById(id);
        if (Objects.isNull(recommend)) {
            throw new CaException("推荐ID不能为空");
        }

        recommend.setHasViewed(Boolean.TRUE);

        caRecommendMapper.updateByPrimaryKeySelective(recommend);
    }

    @Override
    public List<RecommendModel> recommendByRandom(Integer size) throws CaException {
        if (Objects.isNull(size) || size <= 0) {
            throw new CaException("错误的推荐数量");
        }
        Integer userId = RequestUtil.getUserId();

        List<CaUser> userList = userLocalService.listByNotIsUserId(userId);

        List<Integer> friendIds = userFriendService.listFriendIdsByUserId(userId);

        List<CaUser> filterUserList = userList.stream().filter(Objects::nonNull)
                .filter(user -> !friendIds.contains(user.getId())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(filterUserList) || filterUserList.size() < size) {
            filterUserList = userList;
        }

        int userSize = filterUserList.size();
        if (userSize < size) {
            size = userSize;
        }

        int count = 1;
        Random random = new Random();
        Map<Integer, CaUser> recommendUserMapping = Maps.newLinkedHashMap();
        List<Integer> usedIndexList = Lists.newArrayListWithCapacity(size);
        for (; ; ) {
            if (count > size) {
                break;
            }
            int index = random.nextInt(userSize);
            if (!usedIndexList.add(index)) {
                continue;
            }
            CaUser recommendUser = filterUserList.get(index);

            CaRecommend recommend = new CaRecommend();
            recommend.setUserId(userId);
            recommend.setRefId(recommendUser.getId());

            Integer recommendId = save(recommend);
            recommendUserMapping.put(recommendId, recommendUser);

            count++;
        }

        return convertRecommendModelList(recommendUserMapping);
    }

    private List<RecommendModel> convertRecommendModelList(Map<Integer, CaUser> recommendUserMapping) {
        if (MapUtils.isEmpty(recommendUserMapping)) {
            return Lists.newArrayList();
        }

        List<RecommendModel> modelList = Lists.newLinkedList();
        for (Map.Entry<Integer, CaUser> entry : recommendUserMapping.entrySet()) {
            RecommendModel model = new RecommendModel();
            model.setRecommendId(entry.getKey());
            model.setUser(convertUserModel(entry.getValue()));
            modelList.add(model);
        }
        return modelList;
    }

    private SimpleUserModel convertUserModel(CaUser user) {
        if (Objects.isNull(user)) {
            return null;
        }
        SimpleUserModel model = new SimpleUserModel();
        BeanUtils.copyProperties(user, model);
        return model;
    }

    @Override
    public CaRecommend findById(Integer id) throws CaException {
        return caRecommendMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CaRecommend> listTodayByUserId(Integer userId) throws CaException {
        LocalDateTime todayStartTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        Date todayStartDate = Date.from(todayStartTime.atZone(ZoneId.systemDefault()).toInstant());

        CaRecommendExample example = new CaRecommendExample();
        CaRecommendExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andRecommendTimeGreaterThanOrEqualTo(todayStartDate);

        return caRecommendMapper.selectByExample(example);
    }

    @Override
    public List<CaRecommend> listBeforeDaysByUserId(Integer userId, Integer days) throws CaException {
        LocalDateTime todayStartTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        Date todayStartDate = Date.from(todayStartTime.atZone(ZoneId.systemDefault()).toInstant());
        Date beforeNDays = DateUtils.addDays(todayStartDate, days);

        CaRecommendExample example = new CaRecommendExample();
        CaRecommendExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andRecommendTimeGreaterThanOrEqualTo(beforeNDays);

        example.setOrderByClause("recommend_time asc");

        return caRecommendMapper.selectByExample(example);
    }

    private void checkParam(CaRecommend recommend) throws CaException {
        if (Objects.isNull(recommend)) {
            throw new CaException("请求不能为空");
        }
        if (Objects.isNull(recommend.getUserId())) {
            throw new CaException("用户ID不能为空");
        }
        if (Objects.isNull(recommend.getRefId())) {
            throw new CaException("被推荐用户ID不能为空");
        }
    }
}
