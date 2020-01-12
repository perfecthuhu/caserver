package com.card.alumni.service.impl;

import com.card.alumni.dao.CaArticleMapper;
import com.card.alumni.entity.CaArticle;
import com.card.alumni.entity.CaArticleExample;
import com.card.alumni.service.ArticleService;
import com.card.alumni.vo.ArticleVO;
import com.card.alumni.vo.query.ArticleQuery;
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
    public Map<Integer, List<ArticleVO>> queryArticleService(ArticleQuery articleQuery) throws Exception {
        CaArticleExample example = buildCaArticleExample(articleQuery);
        List<CaArticle> caArticles = caArticleMapper.selectByExampleWithBLOBs(example);
        List<ArticleVO> articleVOList = caArticles.stream().map(s -> convertArticleVO(s)).collect(Collectors.toList());
        Map<Integer, List<ArticleVO>> resultMap = new HashMap<>();
        articleVOList.stream().forEach(s -> {
            resultMap.merge(s.getType(), Arrays.asList(s), (o, n) -> {
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

    private ArticleVO convertArticleVO(CaArticle caArticle) {
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(caArticle, articleVO);
        return articleVO;
    }
}

