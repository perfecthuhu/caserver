package com.card.alumni.service.impl;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.dao.CaArticleMapper;
import com.card.alumni.entity.CaArticle;
import com.card.alumni.entity.CaArticleExample;
import com.card.alumni.service.ArticleService;
import com.card.alumni.vo.query.ArticleQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 9:32 PM
 */
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private CaArticleMapper caArticleMapper;

    @Override
    public UnifiedResponse queryArticleService(ArticleQuery articleQuery) {
        CaArticleExample example = new CaArticleExample();

        List<CaArticle> caArticles = caArticleMapper.selectByExample(example);
        return new UnifiedResponse();
    }
}
