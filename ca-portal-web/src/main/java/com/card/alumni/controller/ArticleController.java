package com.card.alumni.controller;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.service.ArticleService;
import com.card.alumni.vo.query.ArticleQuery;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 11:14 PM
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    /**
     * 查询首页新闻列表
     * @return
     */
    @RequestMapping("/page")
    public UnifiedResponse queryArticleService(@RequestBody ArticleQuery articleQuery) throws Exception {
        return new UnifiedResponse(articleService.queryArticleService(articleQuery));
    }

    /**
     * 查询新闻详情
     * @param id
     * @return
     */
    @RequestMapping("/detail")
    public UnifiedResponse queryArticleDetail(Integer id) throws Exception {
        return new UnifiedResponse(articleService.queryArticleDetail(id));
    }
}
