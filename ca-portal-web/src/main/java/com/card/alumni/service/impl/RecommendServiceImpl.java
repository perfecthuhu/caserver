package com.card.alumni.service.impl;

import com.card.alumni.dao.CaRecommendMapper;
import com.card.alumni.entity.CaRecommend;
import com.card.alumni.entity.CaRecommendExample;
import com.card.alumni.exception.CaException;
import com.card.alumni.service.RecommendService;
import com.card.alumni.utils.DateUtils;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
