package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.constant.CaConstants;
import com.card.alumni.dao.CaUserFriendApplyMapper;
import com.card.alumni.entity.CaUser;
import com.card.alumni.entity.CaUserFriend;
import com.card.alumni.entity.CaUserFriendApply;
import com.card.alumni.entity.CaUserFriendApplyExample;
import com.card.alumni.enums.FriendApplyStatusEnum;
import com.card.alumni.exception.CaException;
import com.card.alumni.model.SimpleUserModel;
import com.card.alumni.model.UserFriendApplyModel;
import com.card.alumni.request.UserFriendApplyQueryRequest;
import com.card.alumni.request.UserFriendApplyRequest;
import com.card.alumni.request.UserFriendRequest;
import com.card.alumni.service.UserFriendApplyService;
import com.card.alumni.service.UserFriendService;
import com.card.alumni.service.UserLocalService;
import com.card.alumni.utils.RequestUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 好友申请服务
 *
 * @author liumingyu
 * @date 2019-12-24 11:00 PM
 */
@Service
public class UserFriendApplyServiceImpl implements UserFriendApplyService {

    @Autowired
    private UserLocalService userLocalService;

    @Autowired
    private UserFriendService userFriendService;

    @Autowired
    private CaUserFriendApplyMapper caUserFriendApplyMapper;

    @Override
    public Integer apply(UserFriendApplyRequest request) throws CaException {
        checkParam(request);

        if (Objects.isNull(request.getTargetId())) {
            throw new CaException("目标不能为空");
        }

        CaUserFriendApply apply = convert(request);

        Integer userId = RequestUtil.getUserId();

        CaUserFriend friend = userFriendService.findByUserIdAndFriendId(userId, request.getTargetId());
        if (Objects.nonNull(friend)) {
            throw new CaException("已经是好友了，无需重复申请");
        }

        apply.setSponsorId(userId);
        apply.setUpdater(userId);

        Date now = new Date();
        apply.setCreateTime(now);
        apply.setUpdateTime(now);

        apply.setStatus(FriendApplyStatusEnum.WAITING.getCode());

        caUserFriendApplyMapper.insert(apply);

        return apply.getId();
    }

    @Override
    @Transactional(rollbackFor = {CaException.class, Exception.class})
    public void agree(UserFriendApplyRequest request) throws CaException {
        checkParam(request);

        if (Objects.isNull(request.getId())) {
            throw new CaException("申请ID不能为空");
        }

        CaUserFriendApply apply = findById(request.getId());
        if (Objects.isNull(apply)) {
            throw new CaException("当前申请不存在或已删除");
        }

        Integer userId = RequestUtil.getUserId();
        if (!userId.equals(apply.getTargetId())) {
            throw new CaException("不能处理非自己的好友请求");
        }

        apply.setFeedback(request.getFeedback());
        apply.setUpdateTime(new Date());
        apply.setUpdater(userId);
        apply.setStatus(FriendApplyStatusEnum.AGREE.getCode());

        caUserFriendApplyMapper.updateByPrimaryKeySelective(apply);

        // 分别保存各自的好友关系
        UserFriendRequest sponsorFriendRequest = new UserFriendRequest(apply.getSponsorId(), apply.getTargetId());
        userFriendService.save(sponsorFriendRequest);
        UserFriendRequest targetFriendRequest = new UserFriendRequest(apply.getTargetId(), apply.getSponsorId());
        userFriendService.save(targetFriendRequest);
    }

    @Override
    public void refuse(UserFriendApplyRequest request) throws CaException {
        checkParam(request);

        if (Objects.isNull(request.getId())) {
            throw new CaException("申请ID不能为空");
        }

        CaUserFriendApply apply = findById(request.getId());
        if (Objects.isNull(apply)) {
            throw new CaException("当前申请不存在或已删除");
        }

        Integer userId = RequestUtil.getUserId();
        if (!userId.equals(apply.getTargetId())) {
            throw new CaException("不能处理非自己的好友请求");
        }

        apply.setFeedback(request.getFeedback());
        apply.setUpdateTime(new Date());
        apply.setUpdater(userId);
        apply.setStatus(FriendApplyStatusEnum.REFUSE.getCode());

        caUserFriendApplyMapper.updateByPrimaryKeySelective(apply);
    }

    @Override
    public CaUserFriendApply findById(Integer id) throws CaException {
        if (Objects.isNull(id)) {
            throw new CaException("申请ID不能为空");
        }

        return caUserFriendApplyMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer countByMyUnprocessed() throws CaException {

        CaUserFriendApplyExample example = new CaUserFriendApplyExample();
        CaUserFriendApplyExample.Criteria criteria = example.createCriteria();
        criteria.andTargetIdEqualTo(RequestUtil.getUserId());
        criteria.andStatusEqualTo(FriendApplyStatusEnum.WAITING.getCode());

        List<CaUserFriendApply> applyList = caUserFriendApplyMapper.selectByExample(example);

        return applyList.size();
    }

    @Override
    public List<CaUserFriendApply> listBySponsorIdAndTargetId(Integer sponsorId, Integer targetId) throws CaException {
        if (Objects.isNull(sponsorId) && Objects.isNull(targetId)) {
            throw new CaException("请求参数不能为空");
        }

        CaUserFriendApplyExample example = new CaUserFriendApplyExample();
        CaUserFriendApplyExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(sponsorId)) {
            criteria.andSponsorIdEqualTo(sponsorId);
        }
        if (Objects.nonNull(targetId)) {
            criteria.andTargetIdEqualTo(targetId);
        }

        return caUserFriendApplyMapper.selectByExample(example);
    }

    @Override
    public PageData<UserFriendApplyModel> pageByMyApply(UserFriendApplyQueryRequest request) throws CaException {
        if (Objects.isNull(request)) {
            throw new CaException("查询请求不能为空");
        }
        int page = Objects.isNull(request.getPage()) || request.getPage() < 1 ? 1 : request.getPage();
        int size = Objects.isNull(request.getSize()) || request.getSize() < 1 ? 20 : request.getSize();
        String orderField = StringUtils.isBlank(request.getOrderField()) ? "create_time" : request.getOrderField();
        String orderType = StringUtils.isBlank(request.getOrderType()) ? "desc" : request.getOrderType();

        PageHelper.startPage(page, size);
        CaUserFriendApplyExample example = new CaUserFriendApplyExample();
        CaUserFriendApplyExample.Criteria criteria = example.createCriteria();
        criteria.andSponsorIdEqualTo(RequestUtil.getUserId());
        if (Objects.nonNull(request.getStatus())) {
            criteria.andStatusEqualTo(request.getStatus());
        }

        example.setOrderByClause(orderField + CaConstants.BLANK + orderType);
        List<CaUserFriendApply> applyList = caUserFriendApplyMapper.selectByExample(example);
        PageInfo<CaUserFriendApply> pageInfo = new PageInfo<>(applyList);

        List<UserFriendApplyModel> modelList = convert2ModelList(applyList);
        populateTargetUserList(modelList);

        return new PageData<>(pageInfo.getTotal(), modelList);
    }

    @Override
    public PageData<UserFriendApplyModel> pageByMyHandle(UserFriendApplyQueryRequest request) throws CaException {
        if (Objects.isNull(request)) {
            throw new CaException("查询请求不能为空");
        }
        int page = Objects.isNull(request.getPage()) || request.getPage() < 1 ? 1 : request.getPage();
        int size = Objects.isNull(request.getSize()) || request.getSize() < 1 ? 20 : request.getSize();
        String orderField = StringUtils.isBlank(request.getOrderField()) ? "create_time" : request.getOrderField();
        String orderType = StringUtils.isBlank(request.getOrderType()) ? "desc" : request.getOrderType();

        PageHelper.startPage(page, size);
        CaUserFriendApplyExample example = new CaUserFriendApplyExample();
        CaUserFriendApplyExample.Criteria criteria = example.createCriteria();
        criteria.andTargetIdEqualTo(RequestUtil.getUserId());
        if (Objects.nonNull(request.getStatus())) {
            criteria.andStatusEqualTo(request.getStatus());
        }

        example.setOrderByClause(orderField + CaConstants.BLANK + orderType);
        List<CaUserFriendApply> applyList = caUserFriendApplyMapper.selectByExample(example);
        PageInfo<CaUserFriendApply> pageInfo = new PageInfo<>(applyList);

        List<UserFriendApplyModel> modelList = convert2ModelList(applyList);
        populateSponsorUserList(modelList);

        return new PageData<>(pageInfo.getTotal(), modelList);
    }

    /**
     * 填充目标人列表
     *
     * @param modelList 申请列表
     * @throws CaException e
     */
    private void populateTargetUserList(List<UserFriendApplyModel> modelList) throws CaException {
        if (CollectionUtils.isEmpty(modelList)) {
            return;
        }

        List<Integer> userIdList = modelList.stream().filter(Objects::nonNull)
                .map(UserFriendApplyModel::getTargetId).collect(Collectors.toList());

        Map<Integer, CaUser> userMap = userLocalService.mapByIdList(userIdList);

        modelList.forEach(model -> {
            CaUser user = userMap.get(model.getTargetId());
            if (Objects.nonNull(user)) {
                SimpleUserModel userModel = new SimpleUserModel();
                userModel.setId(user.getId());
                userModel.setSex(user.getSex());
                userModel.setName(user.getName());
                userModel.setPhotoImg(user.getPhotoImg());
                model.setUserModel(userModel);
            }
        });
    }

    /**
     * 填充发起人列表
     *
     * @param modelList 申请列表
     * @throws CaException e
     */
    private void populateSponsorUserList(List<UserFriendApplyModel> modelList) throws CaException {
        if (CollectionUtils.isEmpty(modelList)) {
            return;
        }

        List<Integer> userIdList = modelList.stream().filter(Objects::nonNull)
                .map(UserFriendApplyModel::getSponsorId).collect(Collectors.toList());

        Map<Integer, CaUser> userMap = userLocalService.mapByIdList(userIdList);

        modelList.forEach(model -> {
            CaUser user = userMap.get(model.getSponsorId());
            if (Objects.nonNull(user)) {
                SimpleUserModel userModel = new SimpleUserModel();
                userModel.setId(user.getId());
                userModel.setName(user.getName());
                userModel.setSex(user.getSex());
                userModel.setPhotoImg(user.getPhotoImg());
                model.setUserModel(userModel);
            }
        });
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
        model.setCreateTime(entity.getCreateTime());
        model.setUpdater(entity.getUpdater());
        model.setUpdateTime(entity.getUpdateTime());
        return model;
    }

    private void checkParam(UserFriendApplyRequest request) throws CaException {
        if (Objects.isNull(request)) {
            throw new CaException("申请请求不能为空");
        }
    }

    private CaUserFriendApply convert(UserFriendApplyRequest request) {
        CaUserFriendApply entity = new CaUserFriendApply();
        entity.setId(request.getId());
        entity.setTargetId(request.getTargetId());
        entity.setRemark(request.getRemark());
        entity.setFeedback(request.getFeedback());
        return entity;
    }
}
