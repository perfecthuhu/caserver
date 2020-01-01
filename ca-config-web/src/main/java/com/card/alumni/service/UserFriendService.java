package com.card.alumni.service;

import com.card.alumni.common.PageData;
import com.card.alumni.entity.CaUserFriend;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.SimpleUserModel;
import com.card.alumni.request.UserFriendQueryRequest;
import com.card.alumni.request.UserFriendRequest;

import java.util.List;

/**
 * @author liumingyu
 * @date 2019-12-29 11:20 AM
 */
public interface UserFriendService {

    Integer save(UserFriendRequest request) throws CaConfigException;

    void batchSave(List<Integer> friendIdList) throws CaConfigException;

    void update(UserFriendRequest request) throws CaConfigException;

    void deleteById(Integer id) throws CaConfigException;

    void deleteByUserId(Integer userId) throws CaConfigException;

    void deleteByFriendId(Integer friendId) throws CaConfigException;

    void deleteByUserIdAndFriendId(Integer userId, Integer friendId) throws CaConfigException;

    CaUserFriend findById(Integer id) throws CaConfigException;

    CaUserFriend findByUserIdAndFriendId(Integer userId, Integer friendId) throws CaConfigException;

    List<CaUserFriend> listByFriendId(Integer friendId) throws CaConfigException;

    List<CaUserFriend> listByUserId(Integer userId) throws CaConfigException;

    List<Integer> listFriendIdsByUserId(Integer userId) throws CaConfigException;

    List<CaUserFriend> listByUserIdAndFriendIdList(Integer userId, List<Integer> friendIdList) throws CaConfigException;

    PageData<SimpleUserModel> pageFriendsByRequest(UserFriendQueryRequest request) throws CaConfigException;
}
