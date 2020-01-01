package com.card.alumni.service;

import com.card.alumni.common.PageData;
import com.card.alumni.entity.CaUserFriend;
import com.card.alumni.exception.CaException;
import com.card.alumni.model.UserFriendModel;
import com.card.alumni.request.UserFriendQueryRequest;
import com.card.alumni.request.UserFriendRequest;

import java.util.List;

/**
 * @author liumingyu
 * @date 2019-12-21 6:17 PM
 */
public interface UserFriendService {

    Integer save(UserFriendRequest request) throws CaException;

    void batchSave(List<Integer> friendIdList) throws CaException;

    void update(UserFriendRequest request) throws CaException;

    void deleteById(Integer id) throws CaException;

    void deleteByUserIdAndFriendId(Integer userId, Integer friendId) throws CaException;

    CaUserFriend findById(Integer id) throws CaException;

    CaUserFriend findByUserIdAndFriendId(Integer userId, Integer friendId) throws CaException;

    UserFriendModel findModelById(Integer id) throws CaException;

    List<CaUserFriend> listByUserId(Integer userId) throws CaException;

    List<Integer> listFriendIdsByUserId(Integer userId) throws CaException;

    List<CaUserFriend> listByUserIdAndFriendIdList(Integer userId, List<Integer> friendIdList) throws CaException;

    PageData<UserFriendModel> pageByRequest(UserFriendQueryRequest request) throws CaException;
}
