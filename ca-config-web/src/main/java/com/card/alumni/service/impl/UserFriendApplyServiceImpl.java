package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.constant.CaConstants;
import com.card.alumni.dao.CaUserFriendApplyMapper;
import com.card.alumni.entity.CaUserFriendApply;
import com.card.alumni.entity.CaUserFriendApplyExample;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.UserFriendApplyModel;
import com.card.alumni.request.UserFriendApplyQueryRequest;
import com.card.alumni.service.UserFriendApplyService;
import com.card.alumni.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户好友申请服务
 *
 * @author liumingyu
 * @date 2019-12-29 11:21 AM
 */
@Service
public class UserFriendApplyServiceImpl implements UserFriendApplyService {

    @Autowired
    private UserService userService;

    @Autowired
    private CaUserFriendApplyMapper caUserFriendApplyMapper;

    @Override
    public CaUserFriendApply findById(Integer id) throws CaConfigException {
        if (Objects.isNull(id)) {
            throw new CaConfigException("申请ID不能为空");
        }

        return caUserFriendApplyMapper.selectByPrimaryKey(id);
    }

    @Override
    public UserFriendApplyModel findModelById(Integer id) throws CaConfigException {

        CaUserFriendApply apply = findById(id);

        return convert2Model(apply);
    }

    @Override
    public PageData<UserFriendApplyModel> pageByRequest(UserFriendApplyQueryRequest request) throws CaConfigException {
        if (Objects.isNull(request)) {
            throw new CaConfigException("查询请求不能为空");
        }
        int page = Objects.isNull(request.getPage()) || request.getPage() <= 0 ? 1 : request.getPage();
        int size = Objects.isNull(request.getSize()) || request.getSize() <= 0 ? 20 : request.getSize();
        String orderField = StringUtils.isBlank(request.getOrderField()) ? "create_time" : request.getOrderField();
        String orderType = StringUtils.isBlank(request.getOrderType()) ? "desc" : request.getOrderType();

        PageHelper.startPage(page, size);
        CaUserFriendApplyExample example = new CaUserFriendApplyExample();
        CaUserFriendApplyExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getSponsorId())) {
            criteria.andSponsorIdEqualTo(request.getSponsorId());
        }
        if (Objects.nonNull(request.getTargetId())) {
            criteria.andTargetIdEqualTo(request.getTargetId());
        }
        if (Objects.nonNull(request.getStatus())) {
            criteria.andStatusEqualTo(request.getStatus());
        }
        example.setOrderByClause(orderField + CaConstants.BLANK + orderType);
        List<CaUserFriendApply> applyList = caUserFriendApplyMapper.selectByExample(example);
        PageInfo<CaUserFriendApply> pageInfo = new PageInfo<>(applyList);

        List<UserFriendApplyModel> modelList = convert2ModelList(applyList);
        return new PageData<>(pageInfo.getTotal(), modelList);
    }

    private List<UserFriendApplyModel> convert2ModelList(List<CaUserFriendApply> entries) {
        if (CollectionUtils.isEmpty(entries)) {
            return Lists.newArrayList();
        }

        return entries.stream().filter(Objects::nonNull)
                .map(this::convert2Model).collect(Collectors.toList());
    }

    private UserFriendApplyModel convert2Model(CaUserFriendApply entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        UserFriendApplyModel model = new UserFriendApplyModel();
        model.setId(entity.getId());
        model.setSponsorId(entity.getSponsorId());
        model.setTargetId(entity.getTargetId());
        model.setRemark(entity.getRemark());
        model.setFeedback(entity.getFeedback());
        model.setStatus(entity.getStatus());
        model.setUpdater(entity.getUpdater());
        model.setUpdateTime(entity.getUpdateTime());
        model.setCreateTime(entity.getCreateTime());
        return model;
    }
}
