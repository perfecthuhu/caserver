package com.card.alumni.service;

import com.card.alumni.common.PageData;
import com.card.alumni.entity.CaUserFriendApply;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.UserFriendApplyModel;
import com.card.alumni.request.UserFriendApplyQueryRequest;

/**
 * @author liumingyu
 * @date 2019-12-29 11:21 AM
 */
public interface UserFriendApplyService {

    CaUserFriendApply findById(Integer id) throws CaConfigException;

    UserFriendApplyModel findModelById(Integer id) throws CaConfigException;

    PageData<UserFriendApplyModel> pageByRequest(UserFriendApplyQueryRequest request) throws CaConfigException;
}
