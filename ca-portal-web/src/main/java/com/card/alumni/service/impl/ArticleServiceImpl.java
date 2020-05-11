package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.constant.CaConstants;
import com.card.alumni.dao.CaArticleMapper;
import com.card.alumni.entity.CaAlumni;
import com.card.alumni.entity.CaArticle;
import com.card.alumni.entity.CaArticleExample;
import com.card.alumni.model.ArticleModel;
import com.card.alumni.request.ArticleQueryRequest;
import com.card.alumni.service.ArticleService;
import com.card.alumni.vo.AlumniVO;
import com.card.alumni.vo.ArticleVO;
import com.card.alumni.vo.enums.ArticleTypeEnum;
import com.card.alumni.vo.query.ArticleQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 9:32 PM
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private CaArticleMapper caArticleMapper;

    @Override
    public Map<Integer, List<ArticleVO>> queryArticleService() throws Exception {
        ArticleQuery articleQuery = new ArticleQuery();
        articleQuery.setPageSize(3);

        List<ArticleVO> articleVOS = new ArrayList<>();

        Lists.newArrayList(ArticleTypeEnum.values()).stream().forEach(s -> {
            articleQuery.setType(s.getCode());
            PageData<ArticleVO> articleVOPageData = queryPage(articleQuery);
            articleVOS.addAll(articleVOPageData.getItems());
        });

        Map<Integer, List<ArticleVO>> resultMap = new HashMap<>();
        articleVOS.stream().forEach(s -> {
            resultMap.merge(s.getType(), Lists.newArrayList(s), (o, n) -> {
                o.addAll(n);
                return o;
            });
        });
        return resultMap;
    }

    private CaArticleExample buildCaArticleExample(ArticleQuery articleQuery) {
        CaArticleExample example = new CaArticleExample();
        CaArticleExample.Criteria criteria = example.createCriteria();

        if (Objects.isNull(articleQuery)) {
            return example;
        }

        if (Objects.nonNull(articleQuery.getId())) {
            criteria.andIdEqualTo(articleQuery.getId());
        }

        if (Objects.nonNull(articleQuery.getType())) {
            criteria.andTypeEqualTo(articleQuery.getType());
        }

        if (Objects.nonNull(articleQuery.getPublisher())) {
            criteria.andPublisherEqualTo(articleQuery.getPublisher());
        }

        if (Objects.nonNull(articleQuery.getPublishTimeStart())) {
            criteria.andPublishTimeGreaterThanOrEqualTo(articleQuery.getPublishTimeStart());
        }

        if (Objects.nonNull(articleQuery.getPublishTimeEnd())) {
            criteria.andPublishTimeLessThan(articleQuery.getPublishTimeEnd());
        }

        criteria.andIsPublishEqualTo(true);

        return example;
    }

    @Override
    public ArticleVO queryArticleDetail(Integer id) throws Exception {
        CaArticle caArticle = caArticleMapper.selectByPrimaryKey(id);
        return convertArticleVO(caArticle);
    }

    @Override
    public PageData<ArticleVO> queryPage(ArticleQuery articleQuery) {
        CaArticleExample example = buildCaArticleExample(articleQuery);
        example.setOrderByClause("weights,create_time desc");

        PageHelper.startPage(articleQuery.getPage(), articleQuery.getPageSize());

        List<CaArticle> caArticles = caArticleMapper.selectByExampleWithBLOBs(example);
        PageInfo<CaArticle> pageInfo = new PageInfo<>(caArticles);
        List<ArticleVO> articleVOS = convert2ArticleVO(caArticles);

        PageData<ArticleVO> articleVOPageData = new PageData<>(pageInfo.getTotal(), articleVOS);

        return articleVOPageData;
    }

    @Override
    public List<ArticleModel> listTopByRequest(ArticleQueryRequest request) {
        CaArticleExample example = new CaArticleExample();
        CaArticleExample.Criteria criteria = example.createCriteria();
        criteria.andHasTopEqualTo(Boolean.TRUE);
        criteria.andIsDeleteEqualTo(Boolean.FALSE);
        criteria.andIsPublishEqualTo(Boolean.TRUE);
        if (Objects.nonNull(request)) {
            if (StringUtils.isNotBlank(request.getKeyword())) {
                criteria.andTitleLike(CaConstants.LIKE + request.getKeyword() + CaConstants.LIKE);
            }
            if (Objects.nonNull(request.getType())) {
                criteria.andTypeEqualTo(request.getType());
            }
        }
        example.setOrderByClause("weights desc");
        List<CaArticle> entityList = caArticleMapper.selectByExampleWithBLOBs(example);
        return convert2ModelList(entityList);
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
        model.setPublisher(article.getPublisher());
        model.setPublishTime(article.getPublishTime());
        model.setCreator(article.getCreator());
        model.setCreateTime(article.getCreateTime());
        model.setUpdater(article.getUpdater());
        model.setUpdateTime(article.getUpdateTime());
        return model;
    }
    private List<ArticleVO> convert2ArticleVO(List<CaArticle> caArticles) {
        if (CollectionUtils.isEmpty(caArticles)) {
            return new ArrayList<>();
        }
        return caArticles.stream().map(this::convertArticleVO).collect(Collectors.toList());
    }

    private ArticleVO convertArticleVO(CaArticle caArticle) {
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(caArticle, articleVO);
        return articleVO;
    }
}

