package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.dao.CaArticleMapper;
import com.card.alumni.entity.CaAlumni;
import com.card.alumni.entity.CaArticle;
import com.card.alumni.entity.CaArticleExample;
import com.card.alumni.service.ArticleService;
import com.card.alumni.vo.AlumniVO;
import com.card.alumni.vo.ArticleVO;
import com.card.alumni.vo.enums.ArticleTypeEnum;
import com.card.alumni.vo.query.ArticleQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
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

        if (Objects.nonNull(articleQuery)) {
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

        PageHelper.startPage(articleQuery.getPage(), articleQuery.getPageSize());

        List<CaArticle> caArticles = caArticleMapper.selectByExampleWithBLOBs(example);
        PageInfo<CaArticle> pageInfo = new PageInfo<>(caArticles);
        List<ArticleVO> articleVOS = convert2ArticleVO(caArticles);

        PageData<ArticleVO> articleVOPageData = new PageData<>(pageInfo.getTotal(), articleVOS);

        return articleVOPageData;
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

