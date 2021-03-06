package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.constant.CaConstants;
import com.card.alumni.dao.UserFeedbackMapper;
import com.card.alumni.entity.CaUser;
import com.card.alumni.entity.UserFeedback;
import com.card.alumni.entity.UserFeedbackExample;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.FeedBackModel;
import com.card.alumni.request.FeedBackQueryRequest;
import com.card.alumni.request.FeedBackRequest;
import com.card.alumni.service.FeedBackService;
import com.card.alumni.service.UserService;
import com.card.alumni.utils.RequestUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author sunxiaodong10 2020/1/21
 * @date 5:54 PM
 */
@Service
public class FeedBackServiceImpl implements FeedBackService {

    @Resource
    private UserService userService;

    @Resource
    private UserFeedbackMapper userFeedbackMapper;

    @Override
    public PageData<FeedBackModel> queryPage(FeedBackQueryRequest request) {
        if (Objects.isNull(request)) {
            throw new CaConfigException("查询请求不能为空");
        }

        int page = Objects.isNull(request.getPage()) ? 1 : request.getPage();
        int size = Objects.isNull(request.getSize()) ? 20 : request.getSize();
        String orderField = StringUtils.isBlank(request.getOrderField()) ? "create_time" : request.getOrderField();
        String orderType = StringUtils.isBlank(request.getOrderType()) ? "desc" : request.getOrderType();
        List<CaUser> userList = null;
        if (StringUtils.isNoneEmpty(request.getUserName())){
            userList = userService.findByName(request.getUserName());
        }
        PageHelper.startPage(page, size);
        List<Integer> userIds = null;
        if (CollectionUtils.isNotEmpty(userList)){
            userIds = userList.stream().map(CaUser::getId).collect(Collectors.toList());
        }
        UserFeedbackExample example = buildUserFeedbackExample(request, userIds);
        example.setOrderByClause(orderField + CaConstants.BLANK + orderType);

        List<UserFeedback> userFeedbacks = userFeedbackMapper.selectByExample(example);
        PageInfo<UserFeedback> pageInfo = new PageInfo<>(userFeedbacks);

        List<FeedBackModel> modelList = convert2ModelList(pageInfo);
        populateUserList(modelList);

        return new PageData<>(pageInfo.getTotal(), modelList);

    }

    @Override
    public UserFeedback findById(Integer id) {
        return userFeedbackMapper.selectByPrimaryKey(id);
    }

    @Override
    public FeedBackModel findModeById(Integer id) {

        UserFeedback feedback = findById(id);

        FeedBackModel model = convert2FeedBackModel(feedback);

        populateUserInfo(model);

        return model;
    }

    @Override
    public void resolve(Integer id) {
        UserFeedback feedback = findById(id);
        if (Objects.isNull(feedback)) {
            throw new CaConfigException("反馈不存在");
        }

        feedback.setStatus(0);
        feedback.setUpdateTime(new Date());
        feedback.setHandlerId(RequestUtil.getUserId());

        userFeedbackMapper.updateByPrimaryKeySelective(feedback);
    }

    @Override
    public void batchResolve(FeedBackRequest request) {
        if (Objects.isNull(request)) {
            throw new CaConfigException("请求不能为空");
        }
        if (CollectionUtils.isEmpty(request.getIdList())) {
            throw new CaConfigException("反馈ID集合不能为空");
        }
        UserFeedbackExample example = new UserFeedbackExample();
        example.createCriteria().andIdIn(request.getIdList());

        UserFeedback userFeedback = new UserFeedback();
        userFeedback.setStatus(Objects.isNull(request.getStatus()) ? 0 : request.getStatus());
        userFeedback.setUpdateTime(new Date());
        userFeedback.setHandlerId(RequestUtil.getUserId());

        userFeedbackMapper.updateByExampleSelective(userFeedback, example);
    }

    private void populateUserList(List<FeedBackModel> modelList) {
        if (CollectionUtils.isEmpty(modelList)) {
            return;
        }

        List<Integer> userIdList = modelList.stream().filter(Objects::nonNull).map(FeedBackModel::getUserId).collect(Collectors.toList());

        List<CaUser> userList = userService.listByIdList(userIdList);
        if (CollectionUtils.isEmpty(userIdList)) {
            return;
        }
        Set<Integer> handlerIdList = modelList.stream().filter(Objects::nonNull).map(FeedBackModel::getHandlerId).collect(Collectors.toSet());
        List<CaUser> handlerList = userService.listByIdList(Lists.newArrayList(handlerIdList));

        Map<Integer, CaUser> userMap = userList.stream().filter(Objects::nonNull).collect(Collectors.toMap(CaUser::getId, Function.identity(), (k1, k2) -> k2));
        Map<Integer, CaUser> handlerMap = handlerList.stream().filter(Objects::nonNull).collect(Collectors.toMap(CaUser::getId, Function.identity(), (k1, k2) -> k2));
        modelList.forEach(feedBackModel -> {
            if (Objects.nonNull(feedBackModel.getUserId())) {
                CaUser user = userMap.get(feedBackModel.getUserId());
                CaUser handler = handlerMap.get(feedBackModel.getHandlerId());
                if (Objects.nonNull(user)) {
                    feedBackModel.setUserName(user.getName());
                }
                if (Objects.nonNull(handler)){
                    feedBackModel.setHandlerName(handler.getName());
                }
            }
        });
    }

    private void populateUserInfo(FeedBackModel model) {
        if (Objects.isNull(model) || Objects.isNull(model.getUserId())) {
            return;
        }

        CaUser user = userService.findById(model.getUserId());
        if (Objects.isNull(user)) {
            return;
        }

        CaUser handler = userService.findById(model.getHandlerId());
        if (Objects.isNull(handler)) {
            return;
        }

        model.setUserName(user.getName());
        model.setHandlerName(handler.getName());
    }

    private List<FeedBackModel> convert2ModelList(PageInfo<UserFeedback> pageInfo) {
        if (Objects.isNull(pageInfo)) {
            return new ArrayList<>();
        }
        if (CollectionUtils.isEmpty(pageInfo.getList())) {
            return new ArrayList<>();
        }
        return pageInfo.getList().stream().map(this::convert2FeedBackModel).collect(Collectors.toList());
    }

    private FeedBackModel convert2FeedBackModel(UserFeedback userFeedback) {
        FeedBackModel feedBackModel = new FeedBackModel();
        BeanUtils.copyProperties(userFeedback, feedBackModel);

        return feedBackModel;
    }

    private UserFeedbackExample buildUserFeedbackExample(FeedBackQueryRequest feedBackRequest, List<Integer> userIds) {
        UserFeedbackExample example = new UserFeedbackExample();
        UserFeedbackExample.Criteria criteria = example.createCriteria();
        if (Objects.isNull(feedBackRequest)) {
            return example;
        }
        if (Objects.nonNull(feedBackRequest.getStatus())) {
            criteria.andStatusEqualTo(feedBackRequest.getStatus());
        }
        if (CollectionUtils.isNotEmpty(userIds)){
            criteria.andUserIdIn(userIds);
        }
        return example;
    }
}
