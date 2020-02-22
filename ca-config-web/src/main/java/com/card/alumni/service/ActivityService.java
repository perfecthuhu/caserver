package com.card.alumni.service;

import com.card.alumni.common.PageData;
import com.card.alumni.entity.CaActivity;
import com.card.alumni.entity.CaActivityUserRelation;
import com.card.alumni.exception.CaException;
import com.card.alumni.model.ActivityModel;
import com.card.alumni.model.UserModel;
import com.card.alumni.request.ActivityQueryRequest;
import com.card.alumni.request.ActivityRequest;

import java.util.List;

/**
 * @author liumingyu
 * @date 2020-01-16 11:15 PM
 */
public interface ActivityService {

    Integer save(ActivityRequest request) throws CaException;

    void update(ActivityRequest request) throws CaException;

    void deleteById(Integer id) throws CaException;

    void publish(Integer id) throws CaException;

    Integer saveAndPublish(ActivityRequest request) throws CaException;

    void retract(Integer id) throws CaException;

    CaActivity findById(Integer id) throws CaException;

    ActivityModel findModelById(Integer id) throws CaException;

    PageData<ActivityModel> pageByRequest(ActivityQueryRequest request) throws CaException;

    /**
     * 保存活动用户关联关系
     *
     * @param relation 关联关系
     * @return 关联ID
     */
    Integer saveActivityUserRelation(CaActivityUserRelation relation);

    /**
     * 加入活动
     *
     * @param id     活动ID
     * @param userId 用户ID
     */
    void joinActivity(Integer id, Integer userId);

    /**
     * 退出活动
     *
     * @param id     活动ID
     * @param userId 用户ID
     */
    void exitActivity(Integer id, Integer userId);

    /**
     * 根据活动ID和用户ID查询关联关系
     *
     * @param id     活动ID
     * @param userId 用户ID
     * @return 关联关系
     */
    CaActivityUserRelation findByActivityIdAndUserId(Integer id, Integer userId);

    /**
     * 根据活动ID查询用户ID列表
     *
     * @param id 活动ID
     * @return 用户ID列表
     */
    List<Integer> listUserIdByActivityId(Integer id);

    /**
     * 根据活动ID查询用户列表
     *
     * @param id 活动ID
     * @return 用户列表
     */
    List<UserModel> listUserByActivityId(Integer id);
}
