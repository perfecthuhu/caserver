package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.constant.CaConstants;
import com.card.alumni.dao.CaActivityMapper;
import com.card.alumni.dao.CaActivityUserRelationMapper;
import com.card.alumni.entity.CaActivity;
import com.card.alumni.entity.CaActivityExample;
import com.card.alumni.entity.CaActivityUserRelation;
import com.card.alumni.entity.CaActivityUserRelationExample;
import com.card.alumni.enums.ActivityFlowStatusEnum;
import com.card.alumni.enums.ActivityStatusEnum;
import com.card.alumni.enums.ActivityUserStatusEnum;
import com.card.alumni.exception.CaException;
import com.card.alumni.model.ActivityModel;
import com.card.alumni.model.SimpleUserModel;
import com.card.alumni.request.ActivityQueryRequest;
import com.card.alumni.request.ActivityRequest;
import com.card.alumni.request.ActivityUserQueryRequest;
import com.card.alumni.service.ActivityService;
import com.card.alumni.service.UserService;
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
 * 活动服务
 *
 * @author liumingyu
 * @date 2020-01-16 11:15 PM
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private UserService userService;

    @Autowired
    private CaActivityMapper caActivityMapper;

    @Autowired
    private CaActivityUserRelationMapper caActivityUserRelationMapper;

    @Override
    public Integer save(ActivityRequest request) throws CaException {

        checkParam(request);

        CaActivity activity = convert(request);

        Date now = new Date();
        activity.setCreateTime(now);
        activity.setUpdateTime(now);

        Integer userId = RequestUtil.getUserId();
        activity.setCreator(userId);
        activity.setUpdater(userId);

        activity.setStatus(ActivityStatusEnum.WAITING.getCode());
        activity.setIsDelete(Boolean.FALSE);

        caActivityMapper.insert(activity);

        return activity.getId();
    }

    @Override
    public void update(ActivityRequest request) throws CaException {

        checkParam(request);

        if (Objects.isNull(request.getId())) {
            throw new CaException("活动ID不能为空");
        }

        CaActivity activity = convert(request);

        activity.setUpdateTime(new Date());
        activity.setUpdater(RequestUtil.getUserId());

        caActivityMapper.updateByPrimaryKeySelective(activity);
    }

    @Override
    public void deleteById(Integer id) throws CaException {

        CaActivity caActivity = findById(id);
        if (Objects.isNull(caActivity) || caActivity.getIsDelete()) {
            throw new CaException("当前活动不存在或已被删除");
        }

        Integer userId = RequestUtil.getUserId();

        if (!userId.equals(caActivity.getCreator())) {
            throw new CaException("只有创建人才能操作哦~");
        }

        caActivity.setIsDelete(Boolean.TRUE);
        caActivity.setUpdateTime(new Date());
        caActivity.setUpdater(userId);

        caActivityMapper.updateByPrimaryKeyWithBLOBs(caActivity);
    }

    @Override
    public void publish(Integer id) throws CaException {
        CaActivity activity = findById(id);
        if (Objects.isNull(activity) || activity.getIsDelete()) {
            throw new CaException("当前活动不存在或已被删除");
        }

        Integer userId = RequestUtil.getUserId();
        if (!activity.getCreator().equals(userId)) {
            throw new CaException("只有创建人才能操作哦~");
        }

        Date now = new Date();
        activity.setPublishTime(now);
        activity.setUpdateTime(now);

        activity.setUpdater(userId);
        activity.setPublisher(userId);

        activity.setStatus(ActivityStatusEnum.PUBLISHED.getCode());

        caActivityMapper.updateByPrimaryKeyWithBLOBs(activity);
    }

    @Override
    public Integer saveAndPublish(ActivityRequest request) throws CaException {

        // 如果创建过了则直接发布
        if (Objects.nonNull(request.getId())) {
            CaActivity activity = findById(request.getId());
            if (Objects.nonNull(activity) && !activity.getIsDelete()) {
                publish(request.getId());
                return request.getId();
            }
        }

        // 否则需要新创建再发布
        Integer id = save(request);
        publish(id);

        return id;
    }

    @Override
    public void retract(Integer id) throws CaException {
        CaActivity caActivity = findById(id);
        if (Objects.isNull(caActivity) || caActivity.getIsDelete()) {
            throw new CaException("当前活动不存在或已被删除");
        }

        Integer userId = RequestUtil.getUserId();
        if (!caActivity.getCreator().equals(userId)) {
            throw new CaException("只有创建人才能操作哦~");
        }

        caActivity.setUpdateTime(new Date());
        caActivity.setUpdater(userId);

        caActivity.setStatus(ActivityStatusEnum.RETRACTED.getCode());

        caActivityMapper.updateByPrimaryKeyWithBLOBs(caActivity);
    }

    @Override
    public CaActivity findById(Integer id) throws CaException {
        if (Objects.isNull(id)) {
            throw new CaException("活动ID不能为空");
        }
        return caActivityMapper.selectByPrimaryKey(id);
    }

    @Override
    public ActivityModel findModelById(Integer id) throws CaException {

        CaActivity activity = findById(id);

        return convert2Model(activity);
    }

    @Override
    public List<CaActivity> listByIdList(List<Integer> idList) throws CaException {
        if (CollectionUtils.isEmpty(idList)) {
            return Lists.newArrayList();
        }
        CaActivityExample example = new CaActivityExample();
        CaActivityExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(idList);
        return caActivityMapper.selectByExample(example);
    }

    @Override
    public List<ActivityModel> listModelByIdList(List<Integer> idList) throws CaException {
        List<CaActivity> activityList = listByIdList(idList);
        if (CollectionUtils.isEmpty(activityList)) {
            return Lists.newArrayList();
        }

        return convert2ModelList(activityList);
    }

    @Override
    public PageData<ActivityModel> pageByRequest(ActivityQueryRequest request) throws CaException {
        if (Objects.isNull(request)) {
            throw new CaException("查询请求不能为空");
        }

        int page = Objects.isNull(request.getPage()) || request.getPage() <= 0 ? 1 : request.getPage();
        int size = Objects.isNull(request.getSize()) || request.getSize() <= 0 ? 20 : request.getSize();
        String orderField = StringUtils.isBlank(request.getOrderField()) ? "publish_time" : request.getOrderField();
        String orderType = StringUtils.isBlank(request.getOrderType()) ? "desc" : request.getOrderType();

        PageHelper.startPage(page, size);
        CaActivityExample example = new CaActivityExample();
        CaActivityExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(Boolean.FALSE);
        if (StringUtils.isNotBlank(request.getKeyword())) {
            criteria.andTitleLike(CaConstants.LIKE + request.getKeyword() + CaConstants.LIKE);
        }
        if (Objects.nonNull(request.getFlowStatus())) {
            initFlowStatusCriteria(criteria, request);
        }
        criteria.andStatusEqualTo(ActivityStatusEnum.PUBLISHED.getCode());

        example.setOrderByClause(orderField + CaConstants.BLANK + orderType);
        List<CaActivity> activities = caActivityMapper.selectByExampleWithBLOBs(example);
        PageInfo<CaActivity> pageInfo = new PageInfo<>(activities);

        return new PageData<>(pageInfo.getTotal(), convert2ModelList(activities));
    }

    @Override
    public PageData<ActivityModel> pageMyByRequest(ActivityQueryRequest request) throws CaException {
        if (Objects.isNull(request)) {
            throw new CaException("查询请求不能为空");
        }

        int page = Objects.isNull(request.getPage()) || request.getPage() <= 0 ? 1 : request.getPage();
        int size = Objects.isNull(request.getSize()) || request.getSize() <= 0 ? 20 : request.getSize();
        String orderField = StringUtils.isBlank(request.getOrderField()) ? "create_time" : request.getOrderField();
        String orderType = StringUtils.isBlank(request.getOrderType()) ? "desc" : request.getOrderType();

        PageHelper.startPage(page, size);
        CaActivityExample example = new CaActivityExample();
        CaActivityExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(Boolean.FALSE);
        if (StringUtils.isNotBlank(request.getKeyword())) {
            criteria.andTitleLike(CaConstants.LIKE + request.getKeyword() + CaConstants.LIKE);
        }
        if (Objects.nonNull(request.getFlowStatus())) {
            initFlowStatusCriteria(criteria, request);
        }
        if (Objects.nonNull(request.getStatus()) && request.getStatus() > 0) {
            criteria.andStatusEqualTo(request.getStatus());
        }
        criteria.andCreatorEqualTo(RequestUtil.getUserId());

        example.setOrderByClause(orderField + CaConstants.BLANK + orderType);
        List<CaActivity> activities = caActivityMapper.selectByExampleWithBLOBs(example);
        PageInfo<CaActivity> pageInfo = new PageInfo<>(activities);

        return new PageData<>(pageInfo.getTotal(), convert2ModelList(activities));
    }


    @Override
    public Integer saveActivityUserRelation(CaActivityUserRelation relation) {
        if (Objects.isNull(relation.getActivityId())) {
            throw new CaException("活动ID不能为空");
        }
        if (Objects.isNull(relation.getUserId())) {
            throw new CaException("用户ID不能为空");
        }

        Date now = new Date();
        relation.setCreateTime(now);
        relation.setUpdateTime(now);

        Integer userId = RequestUtil.getUserId();
        relation.setCreator(userId);
        relation.setUpdater(userId);

        relation.setStatus(ActivityUserStatusEnum.JOINED.getCode());
        relation.setIsDelete(Boolean.FALSE);

        caActivityUserRelationMapper.insert(relation);
        return relation.getId();
    }

    @Override
    public void joinActivity(Integer id) {

        CaActivity activity = findById(id);
        if (Objects.isNull(activity)) {
            throw new CaException("活动不存在");
        }

        Integer userId = RequestUtil.getUserId();
        CaActivityUserRelation relation = findByActivityIdAndUserId(id, userId);
        if (Objects.nonNull(relation)) {
            throw new CaException("已经加入了~");
        }

        relation = new CaActivityUserRelation();
        relation.setUserId(userId);
        relation.setActivityId(id);

        saveActivityUserRelation(relation);
    }

    @Override
    public void exitActivity(Integer id) {
        Integer userId = RequestUtil.getUserId();
        CaActivityUserRelation relation = findByActivityIdAndUserId(id, userId);
        if (Objects.isNull(relation)) {
            return;
        }

        relation.setStatus(ActivityUserStatusEnum.EXITED.getCode());
        relation.setUpdater(userId);
        relation.setUpdateTime(new Date());

        caActivityUserRelationMapper.updateByPrimaryKeySelective(relation);
    }

    @Override
    public CaActivityUserRelation findByActivityIdAndUserId(Integer id, Integer userId) {
        CaActivityUserRelationExample example = new CaActivityUserRelationExample();
        CaActivityUserRelationExample.Criteria criteria = example.createCriteria();
        criteria.andActivityIdEqualTo(id);
        criteria.andUserIdEqualTo(userId);
        criteria.andIsDeleteEqualTo(Boolean.FALSE);
        List<CaActivityUserRelation> relationList = caActivityUserRelationMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(relationList)) {
            return null;
        }
        return relationList.get(0);
    }

    @Override
    public List<Integer> listUserIdByActivityId(Integer id) {
        if (Objects.isNull(id)) {
            throw new CaException("活动ID不能为空");
        }
        CaActivityUserRelationExample example = new CaActivityUserRelationExample();
        CaActivityUserRelationExample.Criteria criteria = example.createCriteria();
        criteria.andActivityIdEqualTo(id);
        criteria.andStatusEqualTo(ActivityUserStatusEnum.JOINED.getCode());
        criteria.andIsDeleteEqualTo(Boolean.FALSE);
        example.setOrderByClause("create_time desc");
        List<CaActivityUserRelation> relationList = caActivityUserRelationMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(relationList)) {
            return Lists.newArrayList();
        }

        return relationList.stream().filter(Objects::nonNull)
                .map(CaActivityUserRelation::getUserId).collect(Collectors.toList());
    }

    @Override
    public List<SimpleUserModel> listUserByActivityId(Integer id) {
        List<Integer> userIdList = listUserIdByActivityId(id);
        if (CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayList();
        }

        List<SimpleUserModel> userModelList = userService.listSimpleUserModelByIdList(userIdList);
        Map<Integer, SimpleUserModel> userModelMap = userModelList.stream().filter(Objects::nonNull)
                .collect(Collectors.toMap(SimpleUserModel::getId, Function.identity(), (k1, k2) -> k2));

        List<SimpleUserModel> returnList = Lists.newLinkedList();
        for (Integer userId : userIdList) {
            SimpleUserModel userModel = userModelMap.get(userId);
            if (Objects.isNull(userModel)) {
                continue;
            }
            returnList.add(userModel);
        }

        return returnList;
    }

    @Override
    public PageData<ActivityModel> pageMyJoinActivity(ActivityUserQueryRequest request) {
        if (Objects.isNull(request)) {
            throw new CaException("查询请求不能为空");
        }
        int page = Objects.isNull(request.getPage()) || request.getPage() <= 0 ? 1 : request.getPage();
        int size = Objects.isNull(request.getSize()) || request.getSize() <= 0 ? 20 : request.getSize();
        String orderField = StringUtils.isBlank(request.getOrderField()) ? "create_time" : request.getOrderField();
        String orderType = StringUtils.isBlank(request.getOrderType()) ? "desc" : request.getOrderType();

        Integer userId = RequestUtil.getUserId();
        PageHelper.startPage(page, size);
        CaActivityUserRelationExample example = new CaActivityUserRelationExample();
        CaActivityUserRelationExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andIsDeleteEqualTo(Boolean.FALSE);
        if (Objects.nonNull(request.getStatus()) && request.getStatus() > 0) {
            criteria.andStatusEqualTo(request.getStatus());
        }
        example.setOrderByClause(orderField + CaConstants.BLANK + orderType);
        List<CaActivityUserRelation> relationList = caActivityUserRelationMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(relationList)) {
            return new PageData<>(0, Lists.newArrayList());
        }
        PageInfo<CaActivityUserRelation> pageInfo = new PageInfo<>(relationList);

        List<Integer> activityIdList = relationList.stream().filter(Objects::nonNull).map(CaActivityUserRelation::getActivityId).collect(Collectors.toList());
        List<ActivityModel> activityModelList = listModelByIdList(activityIdList);
        Map<Integer, ActivityModel> activityModelMap = activityModelList.stream().filter(Objects::nonNull)
                .collect(Collectors.toMap(ActivityModel::getId, Function.identity(), (k1, k2) -> k2));

        List<ActivityModel> returnList = Lists.newLinkedList();
        for (Integer activityId : activityIdList) {
            ActivityModel activityModel = activityModelMap.get(activityId);
            if (Objects.isNull(activityModel)) {
                continue;
            }
            returnList.add(activityModel);
        }

        return new PageData<>(pageInfo.getTotal(), returnList);
    }

    private void initFlowStatusCriteria(CaActivityExample.Criteria criteria, ActivityQueryRequest request) {
        Date now = new Date();
        ActivityFlowStatusEnum flowStatusEnum = ActivityFlowStatusEnum.getEnumByType(request.getFlowStatus());
        if (Objects.isNull(flowStatusEnum)) {
            return;
        }
        switch (flowStatusEnum) {
            case WAITING:
                criteria.andStartTimeGreaterThan(now);
                break;
            case PROCESSING:
                criteria.andStartTimeLessThanOrEqualTo(now);
                criteria.andEndTimeGreaterThanOrEqualTo(now);
                break;
            case ENDED:
                criteria.andEndTimeGreaterThan(now);
                break;
            default:
                break;
        }
    }

    private List<ActivityModel> convert2ModelList(List<CaActivity> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Lists.newArrayList();
        }

        return entities.stream().filter(Objects::nonNull)
                .map(this::convert2Model).collect(Collectors.toList());
    }

    private ActivityModel convert2Model(CaActivity entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        ActivityModel model = new ActivityModel();
        model.setId(entity.getId());
        model.setTitle(entity.getTitle());
        model.setSubTitle(entity.getSubTitle());
        model.setContent(entity.getContent());
        model.setStartTime(entity.getStartTime());
        model.setEndTime(entity.getEndTime());
        model.setStatus(entity.getStatus());
        model.setPublisher(entity.getPublisher());
        model.setPublishTime(entity.getPublishTime());
        model.setCreator(entity.getCreator());
        model.setCreateTime(entity.getCreateTime());
        model.setUpdater(entity.getUpdater());
        model.setUpdateTime(entity.getUpdateTime());

        Date now = new Date();
        if (now.getTime() < model.getStartTime().getTime()) {
            model.setFlowStatus(ActivityFlowStatusEnum.WAITING.getCode());
        } else if (now.getTime() > model.getEndTime().getTime()) {
            model.setFlowStatus(ActivityFlowStatusEnum.ENDED.getCode());
        } else {
            model.setFlowStatus(ActivityFlowStatusEnum.PROCESSING.getCode());
        }
        return model;
    }

    private CaActivity convert(ActivityRequest request) {
        CaActivity activity = new CaActivity();
        activity.setId(request.getId());
        activity.setTitle(request.getTitle());
        activity.setSubTitle(request.getSubTitle());
        activity.setContent(request.getContent());
        activity.setStartTime(request.getStartTime());
        activity.setEndTime(request.getEndTime());
        return activity;
    }

    private void checkParam(ActivityRequest request) {
        if (Objects.isNull(request)) {
            throw new CaException("活动请求不能为空");
        }
        if (StringUtils.isBlank(request.getTitle())) {
            throw new CaException("标题不能为空");
        }
        if (StringUtils.isBlank(request.getContent())) {
            throw new CaException("内容不能为空");
        }
        if (Objects.isNull(request.getStartTime())) {
            throw new CaException("开始时间不能为空");
        }
        if (Objects.isNull(request.getEndTime())) {
            throw new CaException("结束时间不能为空");
        }
        if (request.getEndTime().getTime() <= request.getStartTime().getTime()) {
            throw new CaException("非法的活动时间");
        }
    }
}
