package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.constant.CaConstants;
import com.card.alumni.dao.UserFeedbackMapper;
import com.card.alumni.entity.UserFeedback;
import com.card.alumni.entity.UserFeedbackExample;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.FeedBackModel;
import com.card.alumni.request.FeedBackRequest;
import com.card.alumni.service.FeedBackService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author sunxiaodong10 2020/1/21
 * @date 5:54 PM
 */
@Service
public class FeedBackServiceImpl implements FeedBackService {

    @Resource
    private UserFeedbackMapper userFeedbackMapper;

    @Override
    public PageData<FeedBackModel> queryPage(FeedBackRequest request) {
        if (Objects.isNull(request)) {
            throw new CaConfigException("查询请求不能为空");
        }

        int page = Objects.isNull(request.getPage()) ? 1 : request.getPage();
        int size = Objects.isNull(request.getSize()) ? 20 : request.getSize();
        String orderField = StringUtils.isBlank(request.getOrderField()) ? "create_time" : request.getOrderField();
        String orderType = StringUtils.isBlank(request.getOrderType()) ? "desc" : request.getOrderType();

        PageHelper.startPage(page, size);
        UserFeedbackExample example =  buildUserFeedbackExample(request);
        example.setOrderByClause(orderField + CaConstants.BLANK + orderType);

        List<UserFeedback> userFeedbacks = userFeedbackMapper.selectByExample(example);
        PageInfo<UserFeedback> pageInfo = new PageInfo<>(userFeedbacks);

        return new PageData<>(pageInfo.getTotal(), convert2ModelList(pageInfo));

    }

    @Override
    public UserFeedback findById(Integer id) {
        return userFeedbackMapper.selectByPrimaryKey(id);
    }

    @Override
    public FeedBackModel findModeById(Integer id) {

        UserFeedback feedback = new UserFeedback();

        return convert2FeedBackModel(feedback);
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

    private UserFeedbackExample buildUserFeedbackExample(FeedBackRequest feedBackRequest) {
        UserFeedbackExample example = new UserFeedbackExample();
        UserFeedbackExample.Criteria criteria = example.createCriteria();
        if (Objects.isNull(feedBackRequest)) {
            return example;
        }
        if (Objects.nonNull(feedBackRequest.getId())) {
            criteria.andIdEqualTo(feedBackRequest.getId());
        }
        if (Objects.nonNull(feedBackRequest.getUserId())) {
            criteria.andUserIdEqualTo(feedBackRequest.getUserId());
        }
        if (Objects.nonNull(feedBackRequest.getStatus())) {
            criteria.andStatusEqualTo(feedBackRequest.getStatus());
        }

        return example;
    }

    @Override
    public void update(FeedBackRequest feedBackRequest) {
        if (Objects.isNull(feedBackRequest)) {
            throw new CaConfigException("参数为空");
        }
        if (CollectionUtils.isEmpty(feedBackRequest.getIdList())) {
            throw new CaConfigException("参数为空");
        }
        UserFeedbackExample example = new UserFeedbackExample();
        example.createCriteria().andIdIn(feedBackRequest.getIdList());

        UserFeedback userFeedback = new UserFeedback();
        userFeedback.setStatus(0);
        userFeedback.setUpdateTime(new Date(System.currentTimeMillis()));

        userFeedbackMapper.updateByExampleSelective(userFeedback, example);
    }
}
