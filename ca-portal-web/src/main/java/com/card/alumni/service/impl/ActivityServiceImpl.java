package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.dao.CaActivityMapper;
import com.card.alumni.entity.CaActivity;
import com.card.alumni.enums.ActivityStatusEnum;
import com.card.alumni.exception.CaException;
import com.card.alumni.model.ActivityModel;
import com.card.alumni.request.ActivityQueryRequest;
import com.card.alumni.request.ActivityRequest;
import com.card.alumni.service.ActivityService;
import com.card.alumni.utils.RequestUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
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
    private CaActivityMapper caActivityMapper;

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

    }

    @Override
    public void publish(Integer id) throws CaException {

    }

    @Override
    public void saveAndPublish(ActivityRequest request) throws CaException {

    }

    @Override
    public void retract(Integer id) throws CaException {

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
    public PageData<ActivityModel> pageByRequest(ActivityQueryRequest request) throws CaException {
        return null;
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
