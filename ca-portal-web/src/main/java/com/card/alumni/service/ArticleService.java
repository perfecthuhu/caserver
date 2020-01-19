package com.card.alumni.service;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.vo.ArticleVO;
import com.card.alumni.vo.query.ArticleQuery;

import java.util.List;
import java.util.Map;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 7:52 PM
 */
public interface ArticleService {

    /**
     * 查询首页新闻列表
     * @return
     */
    Map<Integer, List<ArticleVO>> queryArticleService() throws Exception;

    /**
     * 查询新闻详情
     * @param id
     * @return
     */
    ArticleVO queryArticleDetail(Integer id) throws Exception;

    /**
     * 分页查询新闻
     * @param articleQuery
     * @return
     */
    PageData<ArticleVO> queryPage(ArticleQuery articleQuery);
}
