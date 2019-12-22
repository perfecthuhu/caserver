package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.constant.CaConstants;
import com.card.alumni.dao.CaUserFriendMapper;
import com.card.alumni.entity.CaUserFriend;
import com.card.alumni.entity.CaUserFriendExample;
import com.card.alumni.exception.CaException;
import com.card.alumni.model.UserFriendModel;
import com.card.alumni.request.UserFriendQueryRequest;
import com.card.alumni.request.UserFriendRequest;
import com.card.alumni.service.UserFriendService;
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
 * 用户好友服务
 *
 * @author liumingyu
 * @date 2019-12-21 6:17 PM
 */
@Service
public class UserFriendServiceImpl implements UserFriendService {

    @Autowired
    private CaUserFriendMapper caUserFriendMapper;

    @Override
    public Integer save(UserFriendRequest request) throws CaException {

        checkParam(request);

        CaUserFriend history = findByUserIdAndFriendId(request.getUserId(), request.getFriendId());
        if (Objects.nonNull(history)) {
            return history.getId();
        }

        CaUserFriend friend = convert(request);

        Date now = new Date();
        friend.setCreateTime(now);
        friend.setUpdateTime(now);

        Integer userId = RequestUtil.getUserId();
        friend.setCreator(userId);
        friend.setUpdater(userId);
        friend.setIsDelete(Boolean.FALSE);

        caUserFriendMapper.insert(friend);

        return friend.getId();
    }

    @Override
    public void batchSave(List<Integer> friendIdList) throws CaException {
        if (CollectionUtils.isEmpty(friendIdList)) {
            return;
        }

        Integer userId = RequestUtil.getUserId();
        List<Integer> friendIds = Lists.newArrayList();
        List<CaUserFriend> historyFriendList = listByUserIdAndFriendIdList(userId, friendIdList);
        if (CollectionUtils.isNotEmpty(historyFriendList)) {
            Map<Integer, CaUserFriend> friendMap = historyFriendList.stream().filter(Objects::nonNull).collect(Collectors.toMap(CaUserFriend::getId, Function.identity(), (k1, k2) -> k2));
            friendIds = friendIdList.stream().filter(Objects::nonNull).filter(friendId -> Objects.isNull(friendMap.get(friendId))).collect(Collectors.toList());
        }

        if (CollectionUtils.isEmpty(friendIds)) {
            return;
        }

        Date now = new Date();
        List<CaUserFriend> friendList = Lists.newArrayList();
        for (Integer friendId : friendIds) {
            CaUserFriend friend = new CaUserFriend();
            friend.setUserId(userId);
            friend.setFriendId(friendId);
            friend.setCreateTime(now);
            friend.setUpdateTime(now);
            friend.setCreator(userId);
            friend.setUpdater(userId);
            friend.setIsDelete(Boolean.FALSE);
            friendList.add(friend);
        }

        caUserFriendMapper.batchInsert(friendList);
    }

    @Override
    public void update(UserFriendRequest request) throws CaException {

        checkParam(request);

        if (Objects.isNull(request.getId())) {
            throw new CaException("主键ID不能为空");
        }

        CaUserFriend friend = convert(request);

        friend.setUpdateTime(new Date());
        friend.setUpdater(RequestUtil.getUserId());

        caUserFriendMapper.updateByPrimaryKeySelective(friend);
    }

    @Override
    public void deleteById(Integer id) throws CaException {

        CaUserFriend friend = findById(id);
        if (Objects.isNull(friend) || friend.getIsDelete()) {
            return;
        }

        friend.setIsDelete(Boolean.TRUE);
        friend.setUpdateTime(new Date());
        friend.setUpdater(RequestUtil.getUserId());

        caUserFriendMapper.updateByPrimaryKeySelective(friend);
    }

    @Override
    public void deleteByUserIdAndFriendId(Integer userId, Integer friendId) throws CaException {
        CaUserFriend friend = findByUserIdAndFriendId(userId, friendId);
        if (Objects.isNull(friend) || friend.getIsDelete()) {
            return;
        }

        friend.setIsDelete(Boolean.TRUE);
        friend.setUpdateTime(new Date());
        friend.setUpdater(RequestUtil.getUserId());

        caUserFriendMapper.updateByPrimaryKeySelective(friend);
    }

    @Override
    public CaUserFriend findById(Integer id) throws CaException {
        if (Objects.isNull(id)) {
            throw new CaException("主键ID不能为空");
        }
        return caUserFriendMapper.selectByPrimaryKey(id);
    }

    @Override
    public CaUserFriend findByUserIdAndFriendId(Integer userId, Integer friendId) throws CaException {

        CaUserFriendExample example = new CaUserFriendExample();
        CaUserFriendExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andFriendIdEqualTo(friendId);
        criteria.andIsDeleteEqualTo(Boolean.FALSE);

        List<CaUserFriend> friendList = caUserFriendMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(friendList)) {
            return null;
        }
        return friendList.get(0);
    }

    @Override
    public UserFriendModel findModelById(Integer id) throws CaException {

        CaUserFriend friend = findById(id);

        return convert2Model(friend);
    }

    @Override
    public List<CaUserFriend> listByUserId(Integer userId) throws CaException {
        if (Objects.isNull(userId)) {
            throw new CaException("用户ID不能为空");
        }

        CaUserFriendExample example = new CaUserFriendExample();
        CaUserFriendExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andIsDeleteEqualTo(Boolean.FALSE);

        return caUserFriendMapper.selectByExample(example);
    }

    @Override
    public List<CaUserFriend> listByUserIdAndFriendIdList(Integer userId, List<Integer> friendIdList) throws CaException {

        CaUserFriendExample example = new CaUserFriendExample();
        CaUserFriendExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        if (CollectionUtils.isNotEmpty(friendIdList)) {
            criteria.andFriendIdIn(friendIdList);
        }

        return caUserFriendMapper.selectByExample(example);
    }

    @Override
    public PageData<UserFriendModel> pageByRequest(UserFriendQueryRequest request) throws CaException {
        int page = Objects.isNull(request.getPage()) ? 1 : request.getPage();
        int size = Objects.isNull(request.getSize()) ? 20 : request.getSize();
        String orderField = StringUtils.isBlank(request.getOrderField()) ? "create_time" : request.getOrderField();
        String orderType = StringUtils.isBlank(request.getOrderType()) ? "desc" : request.getOrderType();

        PageHelper.startPage(page, size);

        CaUserFriendExample example = new CaUserFriendExample();
        CaUserFriendExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(request.getUserId());
        criteria.andIsDeleteEqualTo(Boolean.FALSE);
        example.setOrderByClause(orderField + CaConstants.BLANK + orderType);
        List<CaUserFriend> friendList = caUserFriendMapper.selectByExample(example);
        PageInfo<CaUserFriend> pageInfo = new PageInfo<>(friendList);

        return new PageData<>(pageInfo.getTotal(), convert2ModelList(friendList));
    }

    private void checkParam(UserFriendRequest request) throws CaException {
        if (Objects.isNull(request)) {
            throw new CaException("用户请求不能为空");
        }
        if (Objects.isNull(request.getUserId())) {
            throw new CaException("用户ID不能为空");
        }
        if (Objects.isNull(request.getFriendId())) {
            throw new CaException("好友ID不能为空");
        }
    }

    private CaUserFriend convert(UserFriendRequest request) {
        CaUserFriend entity = new CaUserFriend();
        entity.setId(request.getId());
        entity.setUserId(request.getUserId());
        entity.setFriendId(request.getFriendId());
        return entity;
    }

    private List<UserFriendModel> convert2ModelList(List<CaUserFriend> entries) {
        if (CollectionUtils.isEmpty(entries)) {
            return Lists.newArrayList();
        }

        return entries.stream().filter(Objects::nonNull)
                .map(this::convert2Model).collect(Collectors.toList());
    }

    private UserFriendModel convert2Model(CaUserFriend entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        UserFriendModel model = new UserFriendModel();
        model.setId(entity.getId());
        model.setUserId(entity.getUserId());
        model.setFriendId(entity.getFriendId());
        model.setCreator(entity.getCreator());
        model.setCreateTime(entity.getCreateTime());
        model.setUpdater(entity.getUpdater());
        model.setUpdateTime(entity.getUpdateTime());
        return model;
    }
}