package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.constant.CaConstants;
import com.card.alumni.dao.CaCommentMapper;
import com.card.alumni.entity.CaComment;
import com.card.alumni.entity.CaCommentExample;
import com.card.alumni.entity.CaUser;
import com.card.alumni.enums.CommentTenantEnum;
import com.card.alumni.exception.CaException;
import com.card.alumni.model.CommentModel;
import com.card.alumni.request.CommentQueryRequest;
import com.card.alumni.request.CommentRequest;
import com.card.alumni.service.CommentService;
import com.card.alumni.service.UserService;
import com.card.alumni.utils.RequestUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 评论服务
 *
 * @author liumingyu
 * @date 2020-03-18 11:12 AM
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CaCommentMapper caCommentMapper;

    @Autowired
    private UserService userService;

    @Override
    public Long save(CommentRequest request) {
        checkRequest(request);

        CaComment comment = convert(request);

        Integer userId = RequestUtil.getUserId();
        comment.setCreator(userId);
        comment.setUpdater(userId);

        comment.setCreatorName(RequestUtil.getUserName());
        comment.setCreatorAvatar(RequestUtil.getUserAvatar());

        Date now = new Date();
        comment.setUpdateTime(now);
        comment.setCreateTime(now);

        comment.setIsDelete(Boolean.FALSE);

        if (Objects.nonNull(comment.getRootUserId())) {
            if (StringUtils.isBlank(comment.getRootUserName()) && StringUtils.isBlank(comment.getRootUserAvatar())) {
                CaUser user = userService.findById(comment.getRootUserId());
                if (Objects.nonNull(user)) {
                    comment.setRootUserAvatar(user.getPhotoImg());
                    comment.setRootUserName(user.getName());
                }
            }
        }

        caCommentMapper.insert(comment);

        return comment.getId();
    }

    @Override
    public void deleteById(Long id) {
        CaComment comment = findById(id);
        if (Objects.isNull(comment) || comment.getIsDelete()) {
            throw new CaException("该条评论已经删除,请勿重复操作");
        }

        comment.setUpdater(RequestUtil.getUserId());
        comment.setUpdateTime(new Date());
        comment.setIsDelete(Boolean.TRUE);

        caCommentMapper.updateByPrimaryKeySelective(comment);
    }

    @Override
    public CaComment findById(Long id) {
        if (Objects.isNull(id)) {
            throw new CaException("评论ID不能为空");
        }
        return caCommentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CaComment> listByTenantIdAndTargetId(Integer tenantId, Long targetId, List<Long> rootIdList) {
        checkTenant(tenantId);
        if (Objects.isNull(targetId)) {
            throw new CaException("目标ID不能为空");
        }

        CaCommentExample example = new CaCommentExample();
        CaCommentExample.Criteria criteria = example.createCriteria();
        criteria.andRootIdIn(rootIdList);
        criteria.andTenantIdEqualTo(tenantId);
        criteria.andTargetIdEqualTo(targetId);
        criteria.andIsDeleteEqualTo(Boolean.FALSE);

        return caCommentMapper.selectByExample(example);
    }

    @Override
    public int countByTenantAndTargetId(Integer tenantId, Long targetId) {
        checkTenant(tenantId);
        if (Objects.isNull(targetId)) {
            throw new CaException("目标ID不能为空");
        }

        CaCommentExample example = new CaCommentExample();
        CaCommentExample.Criteria criteria = example.createCriteria();
        criteria.andRootIdEqualTo(0L);
        criteria.andTenantIdEqualTo(tenantId);
        criteria.andTargetIdEqualTo(targetId);
        criteria.andIsDeleteEqualTo(Boolean.FALSE);

        return caCommentMapper.countByExample(example);
    }

    @Override
    public PageData<CommentModel> pageByRequest(CommentQueryRequest request) {
        checkQueryRequest(request);

        int page = Objects.isNull(request.getPage()) || request.getPage() <= 0 ? 1 : request.getPage();
        int size = Objects.isNull(request.getSize()) || request.getSize() <= 0 ? 10 : request.getSize();
        String orderField = StringUtils.isBlank(request.getOrderField()) ? "create_time" : request.getOrderField();
        String orderType = StringUtils.isBlank(request.getOrderType()) ? "asc" : request.getOrderType();

        PageHelper.startPage(page, size);

        CaCommentExample example = new CaCommentExample();
        CaCommentExample.Criteria criteria = example.createCriteria();
        criteria.andRootIdEqualTo(0L);
        criteria.andTenantIdEqualTo(request.getTenantId());
        criteria.andTargetIdEqualTo(request.getTargetId());
        criteria.andIsDeleteEqualTo(Boolean.FALSE);

        example.setOrderByClause(orderField + CaConstants.BLANK + orderType);
        List<CaComment> comments = caCommentMapper.selectByExample(example);
        PageInfo<CaComment> pageInfo = new PageInfo<>(comments);

        List<CommentModel> modelList = convert2ModelList(comments);

        populateChildren(request, modelList);

        return new PageData<>(pageInfo.getTotal(), modelList);
    }

    private void populateChildren(CommentQueryRequest request, List<CommentModel> modelList) {
        if (CollectionUtils.isEmpty(modelList)) {
            return;
        }

        List<Long> rootIdList = modelList.stream().filter(Objects::nonNull).map(CommentModel::getId).collect(Collectors.toList());

        List<CaComment> commentList = listByTenantIdAndTargetId(request.getTenantId(), request.getTargetId(), rootIdList);
        Map<Long, List<CaComment>> commentListMap = commentList.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(CaComment::getRootId));
        modelList.forEach(commentModel -> {
            List<CaComment> subCommentList = commentListMap.get(commentModel.getId());
            commentModel.setChildren(convert2ModelList(subCommentList));
        });
    }

    private List<CommentModel> convert2ModelList(List<CaComment> commentList) {
        if (CollectionUtils.isEmpty(commentList)) {
            return Lists.newArrayList();
        }

        return commentList.stream().filter(Objects::nonNull)
                .map(this::convert2Model).collect(Collectors.toList());
    }

    private CommentModel convert2Model(CaComment entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        CommentModel model = new CommentModel();
        model.setId(entity.getId());
        model.setTenantId(entity.getTenantId());
        model.setTargetId(entity.getTargetId());
        model.setRootId(entity.getRootId());
        model.setContent(entity.getContent());
        model.setRootUserId(entity.getRootUserId());
        model.setRootUserName(entity.getRootUserName());
        model.setRootUserAvatar(entity.getRootUserAvatar());
        model.setCreator(entity.getCreator());
        model.setCreatorName(entity.getCreatorName());
        model.setCreatorAvatar(entity.getCreatorAvatar());
        model.setCreateTime(entity.getCreateTime());
        return model;
    }

    private CaComment convert(CommentRequest request) {
        CaComment entity = new CaComment();
        entity.setTenantId(request.getTenantId());
        entity.setTargetId(request.getTargetId());
        entity.setRootId(request.getRootId());
        entity.setContent(request.getContent());
        entity.setRootUserId(request.getRootUserId());
        entity.setRootUserName(request.getRootUserName());
        entity.setRootUserAvatar(request.getRootUserAvatar());
        return entity;
    }

    private void checkRequest(CommentRequest request) {
        if (Objects.isNull(request)) {
            throw new CaException("请求不能为空");
        }

        if (Objects.isNull(request.getTargetId())) {
            throw new CaException("目标ID不能为空");
        }
        checkTenant(request.getTenantId());

        if (StringUtils.isBlank(request.getContent())) {
            throw new CaException("评论内容不能为空");
        }
        if (Objects.isNull(request.getRootId())) {
            request.setRootId(0L);
        }
        if (request.getRootId() != 0L) {
            if (Objects.isNull(request.getRootUserId())) {
                throw new CaException("被评论人信息不能为空");
            }
        }
    }

    private void checkQueryRequest(CommentQueryRequest request) {
        if (Objects.isNull(request)) {
            throw new CaException("查询请求不能为空");
        }
        if (Objects.isNull(request.getTargetId())) {
            throw new CaException("目标ID不能为空");
        }
        checkTenant(request.getTenantId());
    }

    private void checkTenant(Integer tenantId) {
        CommentTenantEnum tenantEnum = CommentTenantEnum.getEnumByType(tenantId);
        if (Objects.isNull(tenantEnum)) {
            throw new CaException("非法的评论接入方类型");
        }
    }
}
