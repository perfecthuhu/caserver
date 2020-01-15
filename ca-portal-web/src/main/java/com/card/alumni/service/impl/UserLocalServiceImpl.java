package com.card.alumni.service.impl;

import com.card.alumni.dao.CaUserMapper;
import com.card.alumni.entity.CaUser;
import com.card.alumni.entity.CaUserExample;
import com.card.alumni.enums.StatusEnum;
import com.card.alumni.exception.CaException;
import com.card.alumni.service.UserLocalService;
import com.card.alumni.utils.EncryptUtils;
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
 * 本地用户服务
 *
 * @author liumingyu
 * @date 2019-12-21 12:27 PM
 */
@Service
public class UserLocalServiceImpl implements UserLocalService {

    @Autowired
    private CaUserMapper caUserMapper;

    @Override
    public Integer save(CaUser user) throws CaException {
        checkParam(user);

        Date now = new Date();
        user.setCreateTime(now);
        user.setUpdateTime(now);
        user.setYn(StatusEnum.NO.getCode());
        user.setPwdLastResetTime(now);
        user.setPwd(StringUtils.isBlank(user.getPwd()) ?
                EncryptUtils.encryptPassword(user.getPhone()) : EncryptUtils.encryptPassword(user.getPwd()));

        caUserMapper.insert(user);

        return user.getId();
    }

    @Override
    public void update(CaUser user) throws CaException {

        checkParam(user);

        if (Objects.isNull(user.getId())) {
            throw new CaException("用户ID不能为空");
        }

        user.setUpdateTime(new Date());

        caUserMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public CaUser findById(Integer id) throws CaException {
        if (Objects.isNull(id)) {
            throw new CaException("用户ID不能为空");
        }
        return caUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public CaUser findByPhone(String phone) throws CaException {
        if (StringUtils.isBlank(phone)) {
            throw new CaException("手机号不能为空");
        }
        CaUserExample example = new CaUserExample();
        CaUserExample.Criteria criteria = example.createCriteria();
        criteria.andPhoneEqualTo(phone);
        List<CaUser> userList = caUserMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        }
        return userList.get(0);
    }

    @Override
    public List<CaUser> listByIdList(List<Integer> idList) throws CaException {
        if (CollectionUtils.isEmpty(idList)) {
            return Lists.newArrayList();
        }

        List<Integer> ids = idList.stream().distinct().collect(Collectors.toList());

        CaUserExample example = new CaUserExample();
        CaUserExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);

        example.setOrderByClause("friend_name_py asc");
        return caUserMapper.selectByExample(example);
    }

    @Override
    public Map<Integer, CaUser> mapByIdList(List<Integer> idList) throws CaException {

        List<CaUser> userList = listByIdList(idList);

        return userList.stream().filter(Objects::nonNull)
                .collect(Collectors.toMap(CaUser::getId, Function.identity(), (k1, k2) -> k2));
    }

    @Override
    public List<CaUser> listByNotIsUserId(Integer userId) throws CaException {
        if (Objects.isNull(userId)) {
            throw new CaException("用户ID不能为空");
        }
        CaUserExample example = new CaUserExample();
        CaUserExample.Criteria criteria = example.createCriteria();
        criteria.andIdNotEqualTo(userId);

        return caUserMapper.selectByExample(example);
    }

    private void checkParam(CaUser user) throws CaException {
        if (Objects.isNull(user)) {
            throw new CaException("用户信息不能为空");
        }
        if (StringUtils.isBlank(user.getPhone())) {
            throw new CaException("手机号不能为空");
        }
    }
}
