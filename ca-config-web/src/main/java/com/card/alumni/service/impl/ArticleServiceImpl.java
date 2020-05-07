package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.constant.CaConstants;
import com.card.alumni.dao.CaArticleMapper;
import com.card.alumni.entity.CaArticle;
import com.card.alumni.entity.CaArticleExample;
import com.card.alumni.enums.ArticleTypeEnum;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.ArticleModel;
import com.card.alumni.request.ArticleQueryRequest;
import com.card.alumni.request.ArticleRequest;
import com.card.alumni.service.ArticleService;
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
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文章服务
 * 按类型区分
 *
 * @author liumingyu
 * @date 2019-12-10 7:58 PM
 * @see ArticleTypeEnum
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private CaArticleMapper caArticleMapper;

    @Override
    public Integer save(ArticleRequest request) throws CaConfigException {
        checkParam(request);

        CaArticle article = convert(request);

        Integer userId = RequestUtil.getUserId();
        article.setCreator(userId);
        article.setUpdater(userId);

        Date now = new Date();
        article.setCreateTime(now);
        article.setUpdateTime(now);
        article.setTopTime(now);

        article.setIsPublish(Boolean.FALSE);
        article.setIsDelete(Boolean.FALSE);

        caArticleMapper.insert(article);

        return article.getId();
    }

    @Override
    public void update(ArticleRequest request) throws CaConfigException {
        checkParam(request);

        CaArticle article = convert(request);

        update(article);
    }

    @Override
    public void update(CaArticle article) throws CaConfigException {
        if (Objects.isNull(article.getId())) {
            throw new CaConfigException("主键ID不能为空");
        }
        Date now = new Date();
        article.setUpdateTime(now);
        article.setTopTime(now);
        article.setUpdater(RequestUtil.getUserId());

        caArticleMapper.updateByPrimaryKeySelective(article);
    }

    @Override
    public void deleteById(Integer id) throws CaConfigException {
        CaArticle article = findById(id);
        if (Objects.isNull(article) || article.getIsDelete()) {
            throw new CaConfigException("当前文章不存在或已删除");
        }

        article.setIsDelete(Boolean.TRUE);
        article.setUpdateTime(new Date());
        article.setUpdater(RequestUtil.getUserId());

        caArticleMapper.updateByPrimaryKeySelective(article);
    }

    @Override
    public void publish(Integer id) throws CaConfigException {
        CaArticle article = findById(id);
        if (Objects.isNull(article) || article.getIsDelete()) {
            throw new CaConfigException("当前文章不存在或已删除");
        }

        Date now = new Date();
        Integer userId = RequestUtil.getUserId();
        article.setUpdateTime(now);
        article.setUpdater(userId);

        article.setIsPublish(Boolean.TRUE);
        article.setPublisher(userId);
        article.setPublishTime(now);

        caArticleMapper.updateByPrimaryKeyWithBLOBs(article);
    }

    @Override
    public void unPublish(Integer id) throws CaConfigException {
        CaArticle article = findById(id);
        if (Objects.isNull(article) || article.getIsDelete()) {
            throw new CaConfigException("当前文章不存在或已删除");
        }

        Date now = new Date();
        Integer userId = RequestUtil.getUserId();
        article.setUpdateTime(now);
        article.setUpdater(userId);

        article.setIsPublish(Boolean.FALSE);
        article.setPublisher(null);
        article.setPublishTime(null);

        caArticleMapper.updateByPrimaryKeyWithBLOBs(article);
    }

    @Override
    public CaArticle findById(Integer id) throws CaConfigException {
        if (Objects.isNull(id)) {
            throw new CaConfigException("主键ID不能为空");
        }

        return caArticleMapper.selectByPrimaryKey(id);
    }

    @Override
    public ArticleModel findModelById(Integer id) throws CaConfigException {

        CaArticle article = findById(id);

        return convert2Model(article);
    }

    @Override
    public PageData<ArticleModel> pageByRequest(ArticleQueryRequest request) throws CaConfigException {
        if (Objects.isNull(request)) {
            throw new CaConfigException("查询请求不能为空");
        }
        int page = Objects.isNull(request.getPage()) ? 1 : request.getPage();
        int size = Objects.isNull(request.getSize()) ? 20 : request.getSize();
        String orderField = StringUtils.isBlank(request.getOrderField()) ? "create_time" : request.getOrderField();
        String orderType = StringUtils.isBlank(request.getOrderType()) ? "desc" : request.getOrderType();

        PageHelper.startPage(page, size);

        CaArticleExample example = new CaArticleExample();
        CaArticleExample.Criteria criteria = example.createCriteria();
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
        List<CaArticle> articleList = caArticleMapper.selectByExampleWithBLOBs(example);
        PageInfo<CaArticle> pageInfo = new PageInfo<>(articleList);

        return new PageData<>(pageInfo.getTotal(), convert2ModelList(articleList));
    }

    private void checkParam(ArticleRequest request) throws CaConfigException {
        if (Objects.isNull(request)) {
            throw new CaConfigException("请求不能为空");
        }
        if (Objects.isNull(request.getType())) {
            throw new CaConfigException("类型不能为空");
        }
        if (StringUtils.isBlank(request.getTitle())) {
            throw new CaConfigException("标题不能为空");
        }
        if (StringUtils.isBlank(request.getContent())) {
            throw new CaConfigException("内容不能为空");
        }
        ArticleTypeEnum typeEnum = ArticleTypeEnum.getEnumByType(request.getType());
        if (Objects.isNull(typeEnum)) {
            throw new CaConfigException("非法的文章类型");
        }
    }

    private List<ArticleModel> convert2ModelList(List<CaArticle> articleList) {
        if (CollectionUtils.isEmpty(articleList)) {
            return Lists.newArrayList();
        }

        return articleList.stream().filter(Objects::nonNull)
                .map(this::convert2Model).collect(Collectors.toList());
    }

    private ArticleModel convert2Model(CaArticle article) {
        if (Objects.isNull(article)) {
            return null;
        }
        ArticleModel model = new ArticleModel();
        model.setId(article.getId());
        model.setType(article.getType());
        model.setTitle(article.getTitle());
        model.setSubTitle(article.getSubTitle());
        model.setContent(article.getContent());
        model.setIsPublish(article.getIsPublish());
        model.setPublisher(article.getPublisher());
        model.setPublishTime(article.getPublishTime());
        model.setCreator(article.getCreator());
        model.setCreateTime(article.getCreateTime());
        model.setUpdater(article.getUpdater());
        model.setUpdateTime(article.getUpdateTime());
        return model;
    }

    private CaArticle convert(ArticleRequest request) {
        CaArticle entity = new CaArticle();
        entity.setId(request.getId());
        entity.setType(request.getType());
        entity.setTitle(request.getTitle());
        entity.setSubTitle(request.getSubTitle());
        entity.setContent(request.getContent());
        entity.setWeights(request.getWeights());
        entity.setHasTop(request.getHasTop());
        return entity;
    }
}
