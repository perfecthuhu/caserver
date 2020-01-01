package com.card.alumni.service.impl;

import com.card.alumni.dao.CaUserTagMapper;
import com.card.alumni.entity.CaUserTag;
import com.card.alumni.entity.CaUserTagExample;
import com.card.alumni.enums.StatusEnum;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.service.UserTagService;
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
 * @date 2019-12-29 10:58 AM
 */
@Service
public class UserTagServiceImpl implements UserTagService {

    @Autowired
    private CaUserTagMapper caUserTagMapper;

    @Override
    public Integer save(CaUserTag userTag) throws CaConfigException {
        checkParam(userTag);

        userTag.setGirlFriend(Objects.isNull(userTag.getGirlFriend()) ? StatusEnum.NO.getCode() : userTag.getGirlFriend());
        userTag.setFood(Objects.isNull(userTag.getFood()) ? StatusEnum.NO.getCode() : userTag.getFood());
        userTag.setJob(Objects.isNull(userTag.getJob()) ? StatusEnum.NO.getCode() : userTag.getJob());
        userTag.setResource(Objects.isNull(userTag.getResource()) ? StatusEnum.NO.getCode() : userTag.getResource());

        Date now = new Date();
        userTag.setCreateTime(now);
        userTag.setUpdateTime(now);

        caUserTagMapper.insert(userTag);

        return userTag.getId();
    }

    @Override
    public void update(CaUserTag userTag) throws CaConfigException {
        checkParam(userTag);

        userTag.setUpdateTime(new Date());

        caUserTagMapper.updateByPrimaryKeySelective(userTag);
    }

    @Override
    public CaUserTag findByUserId(Integer userId) throws CaConfigException {
        if (Objects.isNull(userId) || userId <= 0) {
            return null;
        }
        CaUserTagExample example = new CaUserTagExample();
        CaUserTagExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<CaUserTag> userTagList = caUserTagMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(userTagList)) {
            return null;
        }
        return userTagList.get(0);
    }

    private void checkParam(CaUserTag userTag) throws CaConfigException {
        if (Objects.isNull(userTag)) {
            throw new CaConfigException("用户标签不能为空");
        }
        if (Objects.isNull(userTag.getUserId())) {
            throw new CaConfigException("用户ID不能为空");
        }
    }
}
