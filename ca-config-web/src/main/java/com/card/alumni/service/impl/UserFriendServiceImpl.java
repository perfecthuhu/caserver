package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.constant.CaConstants;
import com.card.alumni.dao.CaUserFriendMapper;
import com.card.alumni.entity.CaUser;
import com.card.alumni.entity.CaUserFriend;
import com.card.alumni.entity.CaUserFriendExample;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.exception.CaException;
import com.card.alumni.model.SimpleUserModel;
import com.card.alumni.request.UserFriendQueryRequest;
import com.card.alumni.request.UserFriendRequest;
import com.card.alumni.service.UserFriendService;
import com.card.alumni.service.UserService;
import com.card.alumni.utils.RequestUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
 * @date 2019-12-29 11:20 AM
 */
@Service
public class UserFriendServiceImpl implements UserFriendService {

    @Autowired
    private CaUserFriendMapper caUserFriendMapper;

    @Autowired
    private UserService userService;

    @Override
    public Integer save(UserFriendRequest request) throws CaConfigException {

        checkParam(request);

        CaUserFriend history = findByUserIdAndFriendId(request.getUserId(), request.getFriendId());
        if (Objects.nonNull(history)) {
            return history.getId();
        }

        CaUserFriend friend = convert(request);

        Date now = new Date();
        friend.setCreateTime(now);
        friend.setUpdateTime(now);

        friend.setIsDelete(Boolean.FALSE);

        Integer userId = RequestUtil.getUserId();
        friend.setCreator(userId);
        friend.setUpdater(userId);

        caUserFriendMapper.insert(friend);

        return friend.getId();
    }

    @Override
    public void batchSave(List<Integer> friendIdList) throws CaConfigException {
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
            friend.setCreator(userId);
            friend.setUpdater(userId);
            friend.setCreateTime(now);
            friend.setUpdateTime(now);
            friend.setIsDelete(Boolean.FALSE);
            friendList.add(friend);
        }

        caUserFriendMapper.batchInsert(friendList);
    }

    @Override
    public void update(UserFriendRequest request) throws CaConfigException {

        checkParam(request);

        if (Objects.isNull(request.getId())) {
            throw new CaConfigException("主键ID不能为空");
        }

        CaUserFriend friend = convert(request);

        friend.setUpdateTime(new Date());
        friend.setUpdater(RequestUtil.getUserId());

        caUserFriendMapper.updateByPrimaryKeySelective(friend);
    }

    @Override
    public void deleteById(Integer id) throws CaConfigException {

        CaUserFriend friend = findById(id);
        if (Objects.isNull(friend) || friend.getIsDelete()) {
            return;
        }

        friend.setUpdateTime(new Date());
        friend.setIsDelete(Boolean.TRUE);
        friend.setUpdater(RequestUtil.getUserId());

        caUserFriendMapper.updateByPrimaryKeySelective(friend);
    }

    @Override
    public void deleteByUserId(Integer userId) throws CaConfigException {
        List<CaUserFriend> friendList = listByUserId(userId);
        if (CollectionUtils.isEmpty(friendList)) {
            return;
        }

        Date now = new Date();
        Integer operatorId = RequestUtil.getUserId();
        friendList.forEach(friend -> {
            friend.setUpdateTime(now);
            friend.setUpdater(operatorId);
            friend.setIsDelete(Boolean.TRUE);
        });

        caUserFriendMapper.batchUpdate(friendList);
    }

    @Override
    public void deleteByFriendId(Integer friendId) throws CaConfigException {
        List<CaUserFriend> friendList = listByFriendId(friendId);
        if (CollectionUtils.isEmpty(friendList)) {
            return;
        }

        Date now = new Date();
        Integer operatorId = RequestUtil.getUserId();
        friendList.forEach(friend -> {
            friend.setUpdateTime(now);
            friend.setUpdater(operatorId);
            friend.setIsDelete(Boolean.TRUE);
        });

        caUserFriendMapper.batchUpdate(friendList);
    }

    @Override
    @Transactional(rollbackFor = {CaException.class, Exception.class})
    public void deleteByUserIdAndFriendId(Integer userId, Integer friendId) throws CaConfigException {
        CaUserFriend userFriend = findByUserIdAndFriendId(userId, friendId);
        if (Objects.isNull(userFriend) || userFriend.getIsDelete()) {
            return;
        }

        userFriend.setIsDelete(Boolean.TRUE);
        userFriend.setUpdateTime(new Date());
        userFriend.setUpdater(RequestUtil.getUserId());

        caUserFriendMapper.updateByPrimaryKeySelective(userFriend);

        CaUserFriend friendFriend = findByUserIdAndFriendId(friendId, userId);
        if (Objects.isNull(friendFriend) || friendFriend.getIsDelete()) {
            return;
        }

        friendFriend.setUpdateTime(new Date());
        friendFriend.setIsDelete(Boolean.TRUE);
        friendFriend.setUpdater(RequestUtil.getUserId());

        caUserFriendMapper.updateByPrimaryKeySelective(friendFriend);
    }

    @Override
    public CaUserFriend findById(Integer id) throws CaConfigException {
        if (Objects.isNull(id)) {
            throw new CaConfigException("主键ID不能为空");
        }
        return caUserFriendMapper.selectByPrimaryKey(id);
    }

    @Override
    public CaUserFriend findByUserIdAndFriendId(Integer userId, Integer friendId) throws CaConfigException {

        CaUserFriendExample example = new CaUserFriendExample();
        CaUserFriendExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(Boolean.FALSE);
        criteria.andUserIdEqualTo(userId);
        criteria.andFriendIdEqualTo(friendId);

        List<CaUserFriend> friendList = caUserFriendMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(friendList)) {
            return null;
        }
        return friendList.get(0);
    }

    @Override
    public List<CaUserFriend> listByFriendId(Integer friendId) throws CaConfigException {
        if (Objects.isNull(friendId)) {
            throw new CaConfigException("用户ID不能为空");
        }

        CaUserFriendExample example = new CaUserFriendExample();
        CaUserFriendExample.Criteria criteria = example.createCriteria();
        criteria.andFriendIdEqualTo(friendId);
        criteria.andIsDeleteEqualTo(Boolean.FALSE);

        return caUserFriendMapper.selectByExample(example);
    }

    @Override
    public List<CaUserFriend> listByUserId(Integer userId) throws CaConfigException {
        if (Objects.isNull(userId)) {
            throw new CaConfigException("用户ID不能为空");
        }

        CaUserFriendExample example = new CaUserFriendExample();
        CaUserFriendExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andIsDeleteEqualTo(Boolean.FALSE);

        return caUserFriendMapper.selectByExample(example);
    }

    @Override
    public List<Integer> listFriendIdsByUserId(Integer userId) throws CaConfigException {
        List<CaUserFriend> friendList = listByUserId(userId);
        if (CollectionUtils.isEmpty(friendList)) {
            return Lists.newArrayList();
        }

        return friendList.stream().filter(Objects::nonNull)
                .map(CaUserFriend::getFriendId).collect(Collectors.toList());
    }

    @Override
    public List<CaUserFriend> listByUserIdAndFriendIdList(Integer userId, List<Integer> friendIdList) throws CaConfigException {

        CaUserFriendExample example = new CaUserFriendExample();
        CaUserFriendExample.Criteria criteria = example.createCriteria();
        if (CollectionUtils.isNotEmpty(friendIdList)) {
            criteria.andFriendIdIn(friendIdList);
        }
        criteria.andUserIdEqualTo(userId);

        return caUserFriendMapper.selectByExample(example);
    }

    @Override
    public PageData<SimpleUserModel> pageFriendsByRequest(UserFriendQueryRequest request) throws CaConfigException {
        Integer userId = Objects.isNull(request.getUserId()) ? RequestUtil.getUserId() : request.getUserId();
        int page = Objects.isNull(request.getPage()) ? 1 : request.getPage();
        int size = Objects.isNull(request.getSize()) ? 20 : request.getSize();
        String orderField = StringUtils.isBlank(request.getOrderField()) ? "create_time" : request.getOrderField();
        String orderType = StringUtils.isBlank(request.getOrderType()) ? "desc" : request.getOrderType();

        PageHelper.startPage(page, size);

        CaUserFriendExample example = new CaUserFriendExample();
        CaUserFriendExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(Boolean.FALSE);
        criteria.andUserIdEqualTo(userId);
        example.setOrderByClause(orderField + CaConstants.BLANK + orderType);
        List<CaUserFriend> friendList = caUserFriendMapper.selectByExample(example);
        PageInfo<CaUserFriend> pageInfo = new PageInfo<>(friendList);

        return new PageData<>(pageInfo.getTotal(), convert2UserModelList(friendList));
    }

    private void checkParam(UserFriendRequest request) throws CaConfigException {
        if (Objects.isNull(request)) {
            throw new CaConfigException("用户请求不能为空");
        }
        if (Objects.isNull(request.getUserId())) {
            throw new CaConfigException("用户ID不能为空");
        }
        if (Objects.isNull(request.getFriendId())) {
            throw new CaConfigException("好友ID不能为空");
        }
    }

    private CaUserFriend convert(UserFriendRequest request) {
        CaUserFriend entity = new CaUserFriend();
        entity.setUserId(request.getUserId());
        entity.setFriendId(request.getFriendId());
        entity.setId(request.getId());
        return entity;
    }

    private List<SimpleUserModel> convert2UserModelList(List<CaUserFriend> entries) throws CaConfigException {
        if (CollectionUtils.isEmpty(entries)) {
            return Lists.newArrayList();
        }

        List<Integer> friendIdList = entries.stream()
                .filter(Objects::nonNull).map(CaUserFriend::getFriendId).collect(Collectors.toList());

        Map<Integer, CaUser> userMap = userService.mapByIdList(friendIdList);
        if (MapUtils.isEmpty(userMap)) {
            return Lists.newArrayList();
        }

        List<SimpleUserModel> userModels = Lists.newLinkedList();
        entries.forEach(entry -> {
            CaUser user = userMap.get(entry.getFriendId());
            if (Objects.nonNull(user)) {
                SimpleUserModel model = new SimpleUserModel();
                BeanUtils.copyProperties(user, model);
                userModels.add(model);
            }
        });

        return userModels;
    }
}
