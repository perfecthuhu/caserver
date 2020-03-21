package com.card.alumni.service;

import com.card.alumni.common.PageData;
import com.card.alumni.entity.CaUserFriendApply;
import com.card.alumni.enums.FriendApplyStatusEnum;
import com.card.alumni.exception.CaException;
import com.card.alumni.model.UserFriendApplyModel;
import com.card.alumni.request.UserFriendApplyQueryRequest;
import com.card.alumni.request.UserFriendApplyRequest;

import java.util.List;

/**
 * @author liumingyu
 * @date 2019-12-24 11:00 PM
 */
public interface UserFriendApplyService {

    /**
     * 发起好友申请
     *
     * @param request 请求
     * @return 申请ID
     * @throws CaException e
     */
    Integer apply(UserFriendApplyRequest request) throws CaException;

    /**
     * 同意好友申请
     *
     * @param request 请求
     * @throws CaException e
     */
    void agree(UserFriendApplyRequest request) throws CaException;

    /**
     * 拒绝好友申请
     *
     * @param request 请求
     * @throws CaException e
     */
    void refuse(UserFriendApplyRequest request) throws CaException;

    /**
     * 根据申请ID查询申请详情
     *
     * @param id 申请ID
     * @return 申请详情
     * @throws CaException e
     */
    CaUserFriendApply findById(Integer id) throws CaException;

    /**
     * 根据发起人和目标人查询申请关系
     *
     * @param sponsorId 发起人ID
     * @param targetId  目标ID
     * @param status    好友申请枚举
     * @return 申请关系
     */
    CaUserFriendApply findBySponsorIdAndTargetId(Integer sponsorId, Integer targetId, FriendApplyStatusEnum status);

    /**
     * 查询我未处理的好友申请数量
     *
     * @return 未处理的好友申请数量
     * @throws CaException e
     */
    Integer countByMyUnprocessed() throws CaException;

    /**
     * 根据发起人和目标人查询申请列表
     *
     * @param sponsorId 发起人ID
     * @param targetId  目标人ID
     * @return 申请列表
     * @throws CaException e
     */
    List<CaUserFriendApply> listBySponsorIdAndTargetId(Integer sponsorId, Integer targetId) throws CaException;

    /**
     * 分页查询我的申请列表
     *
     * @param request 请求
     * @return 申请列表
     * @throws CaException e
     */
    PageData<UserFriendApplyModel> pageByMyApply(UserFriendApplyQueryRequest request) throws CaException;

    /**
     * 分页查询我的好友申请列表
     *
     * @param request 请求
     * @return 好友申请列表
     * @throws CaException e
     */
    PageData<UserFriendApplyModel> pageByMyHandle(UserFriendApplyQueryRequest request) throws CaException;
}
