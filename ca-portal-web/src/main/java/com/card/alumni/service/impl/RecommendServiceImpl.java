package com.card.alumni.service.impl;

import com.card.alumni.dao.CaRecommendMapper;
import com.card.alumni.entity.CaRecommend;
import com.card.alumni.entity.CaRecommendExample;
import com.card.alumni.entity.CaUser;
import com.card.alumni.exception.CaException;
import com.card.alumni.service.RecommendService;
import com.card.alumni.service.UserFriendService;
import com.card.alumni.service.UserLocalService;
import com.card.alumni.utils.DateUtils;
import com.card.alumni.utils.RequestUtil;
import com.card.alumni.vo.UserVO;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
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
    public List<UserVO> recommend(Integer size) throws CaException {
        Integer userId = RequestUtil.getUserId();

        List<CaRecommend> recommendList = listBeforeDaysByUserId(userId, 3);
        List<Integer> refIdList = recommendList.stream()
                .filter(Objects::nonNull).map(CaRecommend::getRefId).collect(Collectors.toList());

        List<CaUser> userList = userLocalService.listByNotIsUserId(userId);

        List<CaUser> shouldRandomUserList = userList.stream().filter(user -> StringUtils.isBlank(user.getPhotoImg()))
                .filter(Objects::nonNull).filter(user -> !refIdList.contains(user.getId())).collect(Collectors.toList());

        List<CaUser> resultList = getRandom(shouldRandomUserList, size);
        if (resultList.size() < size) {
            int diffCount = size - resultList.size();
            List<CaRecommend> shouldAddList = Lists.newArrayList();
            for (int i = 0; i < diffCount; i++) {
                shouldAddList.add(recommendList.get(i));
            }
            List<Integer> shouldAddUserIdList = shouldAddList.stream()
                    .filter(Objects::nonNull).map(CaRecommend::getUserId).collect(Collectors.toList());
            List<CaUser> shouldAddUserList = userLocalService.listByIdList(shouldAddUserIdList);
            resultList.addAll(shouldAddUserList);
        }

        return convertUserVOList(resultList);
    }

    private List<UserVO> convertUserVOList(List<CaUser> caUsers) {
        if (CollectionUtils.isEmpty(caUsers)) {
            return null;
        }
        return caUsers.stream().filter(Objects::nonNull).map(s -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(s, userVO);
            return userVO;
        }).collect(Collectors.toList());
    }

    private List<CaUser> getRandom(List<CaUser> userList, Integer size) throws CaException {
        if (CollectionUtils.isEmpty(userList)) {
            return Lists.newArrayList();
        }

        Integer userSize = userList.size();

        if (userSize < size) {
            size = userSize;
        }

        Random random = new Random(size);
        List<CaUser> resultList = Lists.newArrayList();
        for (int i = 0; i < size; i++) {
            int index = random.nextInt();
            resultList.add(userList.get(index));
        }

        return resultList;
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

        example.setOrderByClause("create_time asc");

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
