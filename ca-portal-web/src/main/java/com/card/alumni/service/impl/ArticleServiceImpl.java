package com.card.alumni.service.impl;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.dao.CaArticleMapper;
import com.card.alumni.entity.CaArticle;
import com.card.alumni.entity.CaArticleExample;
import com.card.alumni.service.ArticleService;
import com.card.alumni.vo.ArticleVO;
import com.card.alumni.vo.query.ArticleQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public UnifiedResponse queryArticleService(ArticleQuery articleQuery) {
        CaArticleExample example = new CaArticleExample();
        List<CaArticle> caArticles = caArticleMapper.selectByExample(example);
        List<ArticleVO> articleVOList = caArticles.stream().map(s -> convertArticleVO(s)).collect(Collectors.toList());
        Map<Integer, List<ArticleVO>> resultMap = new HashMap<>();
        articleVOList.stream().forEach(s -> {
            resultMap.merge(s.getType(), Arrays.asList(s), (o, n) -> {
                o.addAll(n);
                return o;
            });
        });
        return new UnifiedResponse(resultMap);
    }

    @Override
    public UnifiedResponse queryArticleDetail(Integer id) {
        CaArticle caArticle = caArticleMapper.selectByPrimaryKey(id);
        return new UnifiedResponse(convertArticleVO(caArticle));
    }

    private ArticleVO convertArticleVO(CaArticle caArticle) {
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(caArticle, articleVO);
        return articleVO;
    }
}

