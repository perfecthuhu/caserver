package com.card.alumni.service.impl;

import com.card.alumni.dao.CaUserTagMapper;
import com.card.alumni.entity.CaUserTag;
import com.card.alumni.entity.CaUserTagExample;
import com.card.alumni.exception.CaException;
import com.card.alumni.service.UserTagService;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 用户标签服务
 *
 * @author liumingyu
 * @date 2019-12-20 10:39 PM
 */
@Service
public class UserTagServiceImpl implements UserTagService {

    @Autowired
    private CaUserTagMapper caUserTagMapper;

    @Override
    public Integer save(CaUserTag userTag) throws CaException {
        checkParam(userTag);

        Date now = new Date();
        userTag.setCreateTime(now);
        userTag.setUpdateTime(now);

        caUserTagMapper.insert(userTag);

        return userTag.getId();
    }

    @Override
    public void update(CaUserTag userTag) throws CaException {
        checkParam(userTag);

        if (Objects.isNull(userTag.getId())) {
            throw new CaException("用户标签ID不能为空");
        }

        userTag.setUpdateTime(new Date());

        caUserTagMapper.updateByPrimaryKeySelective(userTag);
    }

    @Override
    public CaUserTag findById(Integer id) throws CaException {
        return caUserTagMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CaUserTag> listByUserIdList(List<Integer> userIdList) throws CaException {
        if (CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayList();
        }

        CaUserTagExample example = new CaUserTagExample();
        CaUserTagExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdIn(userIdList);

        return caUserTagMapper.selectByExample(example);
    }

    @Override
    public List<CaUserTag> listByTags(CaUserTag userTag) throws CaException {
        CaUserTagExample example = new CaUserTagExample();
        CaUserTagExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(userTag.getFood())) {
            criteria.andFoodEqualTo(userTag.getFood());
        }
        if (Objects.nonNull(userTag.getGirlFriend())) {
            criteria.andGirlFriendEqualTo(userTag.getGirlFriend());
        }
        if (Objects.nonNull(userTag.getJob())) {
            criteria.andJobEqualTo(userTag.getJob());
        }
        if (Objects.nonNull(userTag.getResource())) {
            criteria.andResourceEqualTo(userTag.getResource());
        }
        return caUserTagMapper.selectByExample(example);
    }

    private void checkParam(CaUserTag userTag) throws CaException {
        if (Objects.isNull(userTag)) {
            throw new CaException("用户标签不能为空");
        }
        if (Objects.isNull(userTag.getUserId())) {
            throw new CaException("用户ID不能为空");
        }
    }
}
