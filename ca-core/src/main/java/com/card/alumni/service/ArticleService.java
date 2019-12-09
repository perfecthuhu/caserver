package com.card.alumni.service;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.vo.query.ArticleQuery;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 7:52 PM
 */
public interface ArticleService {

    /**
     * 查询新闻
     * @return
     */
    UnifiedResponse queryArticleService(ArticleQuery articleQuery);
}
