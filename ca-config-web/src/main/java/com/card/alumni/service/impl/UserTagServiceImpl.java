package com.card.alumni.service.impl;

import com.card.alumni.dao.CaUserTagMapper;
import com.card.alumni.entity.CaUserTag;
import com.card.alumni.entity.CaUserTagExample;
import com.card.alumni.exception.CaException;
import com.card.alumni.service.UserTagService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public CaUserTag findByUserId(Integer userId) throws CaException {
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
}
