package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.constant.CaConstants;
import com.card.alumni.dao.CaProjectMapper;
import com.card.alumni.entity.CaProject;
import com.card.alumni.entity.CaProjectExample;
import com.card.alumni.enums.ArticleTypeEnum;
import com.card.alumni.enums.ProjectTypeEnum;
import com.card.alumni.exception.CaException;
import com.card.alumni.model.ProjectModel;
import com.card.alumni.request.ProjectQueryRequest;
import com.card.alumni.request.ProjectRequest;
import com.card.alumni.service.ProjectService;
import com.card.alumni.utils.RequestUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
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
 * 项目服务
 *
 * @author liumingyu
 * @date 2019-12-10 7:58 PM
 * @see ArticleTypeEnum
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private CaProjectMapper caProjectMapper;

    @Override
    public Integer save(ProjectRequest request) {
        checkParam(request);

        CaProject entity = convert(request);

        Integer userId = RequestUtil.getUserId();
        entity.setCreator(userId);
        entity.setUpdater(userId);

        Date now = new Date();
        entity.setUpdateTime(now);
        entity.setCreateTime(now);

        entity.setIsPublish(Boolean.FALSE);
        entity.setIsDelete(Boolean.FALSE);

        caProjectMapper.insert(entity);

        return entity.getId();
    }

    @Override
    public void update(ProjectRequest request) {
        checkParam(request);

        CaProject project = convert(request);

        update(project);
    }

    @Override
    public void updateViewCount(Integer id, Long viewCount) {
        CaProject project = findById(id);
        if (Objects.isNull(project) || project.getIsDelete()) {
            throw new CaException("当前项目不存在或已删除");
        }
        if (Objects.isNull(viewCount) || viewCount < 0) {
            throw new CaException("非法的浏览次数");
        }
        project.setViewCount(viewCount);

        update(project);
    }

    @Override
    public void update(CaProject entity) {
        if (Objects.isNull(entity.getId())) {
            throw new CaException("主键ID不能为空");
        }

        entity.setUpdateTime(new Date());
        entity.setUpdater(RequestUtil.getUserId());

        caProjectMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public void deleteById(Integer id) {
        CaProject entity = findById(id);
        if (Objects.isNull(entity) || entity.getIsDelete()) {
            throw new CaException("当前项目不存在或已删除");
        }

        entity.setUpdateTime(new Date());
        entity.setIsDelete(Boolean.TRUE);
        entity.setUpdater(RequestUtil.getUserId());

        caProjectMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public void publish(Integer id) {
        CaProject entity = findById(id);
        if (Objects.isNull(entity) || entity.getIsDelete()) {
            throw new CaException("当前项目不存在或已删除");
        }

        Date now = new Date();
        Integer userId = RequestUtil.getUserId();
        entity.setUpdater(userId);
        entity.setUpdateTime(now);

        entity.setIsPublish(Boolean.TRUE);
        entity.setPublisher(userId);
        entity.setPublishTime(now);

        caProjectMapper.updateByPrimaryKeyWithBLOBs(entity);
    }

    @Override
    public void unPublish(Integer id) {
        CaProject entity = findById(id);
        if (Objects.isNull(entity) || entity.getIsDelete()) {
            throw new CaException("当前项目不存在或已删除");
        }

        Date now = new Date();
        Integer userId = RequestUtil.getUserId();
        entity.setUpdater(userId);
        entity.setUpdateTime(now);

        entity.setIsPublish(Boolean.FALSE);
        entity.setPublisher(null);
        entity.setPublishTime(null);

        caProjectMapper.updateByPrimaryKeyWithBLOBs(entity);
    }

    @Override
    public CaProject findById(Integer id) {
        if (Objects.isNull(id)) {
            throw new CaException("主键ID不能为空");
        }

        return caProjectMapper.selectByPrimaryKey(id);
    }

    @Override
    public ProjectModel findModelById(Integer id) {

        CaProject entity = findById(id);

        return convert2Model(entity);
    }

    @Override
    public PageData<ProjectModel> pageByRequest(ProjectQueryRequest request) {
        if (Objects.isNull(request)) {
            throw new CaException("查询请求不能为空");
        }
        int page = Objects.isNull(request.getPage()) ? 1 : request.getPage();
        int size = Objects.isNull(request.getSize()) ? 20 : request.getSize();
        String orderField = StringUtils.isBlank(request.getOrderField()) ? "create_time" : request.getOrderField();
        String orderType = StringUtils.isBlank(request.getOrderType()) ? "desc" : request.getOrderType();

        PageHelper.startPage(page, size);

        CaProjectExample example = new CaProjectExample();
        CaProjectExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(Boolean.FALSE);
        if (StringUtils.isNotBlank(request.getKeyword())) {
            criteria.andTitleLike(CaConstants.LIKE + request.getKeyword() + CaConstants.LIKE);
        }
        if (Objects.nonNull(request.getType())) {
            criteria.andTypeEqualTo(request.getType());
        }
        if (Objects.nonNull(request.getIsPublish())) {
            criteria.andIsPublishEqualTo(request.getIsPublish());
        }

        example.setOrderByClause(orderField + CaConstants.BLANK + orderType);
        List<CaProject> articleList = caProjectMapper.selectByExampleWithBLOBs(example);
        PageInfo<CaProject> pageInfo = new PageInfo<>(articleList);

        return new PageData<>(pageInfo.getTotal(), convert2ModelList(articleList));
    }

    private void checkParam(ProjectRequest request) {
        if (Objects.isNull(request)) {
            throw new CaException("请求不能为空");
        }
        if (StringUtils.isBlank(request.getTitle())) {
            throw new CaException("标题不能为空");
        }
        if (Objects.isNull(request.getType())) {
            throw new CaException("类型不能为空");
        }
        if (StringUtils.isBlank(request.getContent())) {
            throw new CaException("内容不能为空");
        }
        ProjectTypeEnum typeEnum = ProjectTypeEnum.getEnumByType(request.getType());
        if (Objects.isNull(typeEnum)) {
            throw new CaException("非法的项目类型");
        }
    }

    private List<ProjectModel> convert2ModelList(List<CaProject> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Lists.newArrayList();
        }

        return entities.stream().filter(Objects::nonNull)
                .map(this::convert2Model).collect(Collectors.toList());
    }

    private ProjectModel convert2Model(CaProject entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        ProjectModel model = new ProjectModel();
        model.setId(entity.getId());
        model.setType(entity.getType());
        model.setTitle(entity.getTitle());
        model.setSubTitle(entity.getSubTitle());
        model.setContent(entity.getContent());
        model.setIsPublish(entity.getIsPublish());
        model.setPublisher(entity.getPublisher());
        model.setPublishTime(entity.getPublishTime());
        model.setCreator(entity.getCreator());
        model.setUpdater(entity.getUpdater());
        model.setCreateTime(entity.getCreateTime());
        model.setUpdateTime(entity.getUpdateTime());
        model.setViewCount(entity.getViewCount());
        List<String> pictures = Lists.newArrayList();
        if (StringUtils.isNotBlank(entity.getPicture())) {
            pictures = Splitter.on(CaConstants.COMMA).omitEmptyStrings().trimResults().splitToList(entity.getPicture());
        }
        model.setPictures(pictures);
        return model;
    }

    private CaProject convert(ProjectRequest request) {
        CaProject entity = new CaProject();
        entity.setId(request.getId());
        entity.setTitle(request.getTitle());
        entity.setType(request.getType());
        entity.setSubTitle(request.getSubTitle());
        entity.setContent(request.getContent());
        if (CollectionUtils.isNotEmpty(request.getPictures())) {
            entity.setPicture(Joiner.on(CaConstants.COMMA).skipNulls().join(request.getPictures()));
        }
        return entity;
    }
}
