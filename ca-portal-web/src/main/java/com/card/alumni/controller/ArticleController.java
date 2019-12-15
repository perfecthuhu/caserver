package com.card.alumni.controller;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.service.ArticleService;
import com.card.alumni.vo.query.ArticleQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 11:14 PM
 */
@RestController
@RequestMapping("/article")
@Api(value = "新闻模块", tags = "新闻模块")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    /**
     * 查询首页新闻列表
     * @return
     */
    @PostMapping("/page")
    @ApiOperation(value = "查询首页新闻列表", notes = "查询首页新闻列表", response = UnifiedResponse.class)
    public UnifiedResponse queryArticleService(@RequestBody ArticleQuery articleQuery) throws Exception {
        return new UnifiedResponse(articleService.queryArticleService(articleQuery));
    }

    /**
     * 查询新闻详情
     * @param id
     * @return
     */
    @GetMapping("/detail")
    @ApiOperation(value = "查询新闻详情", notes = "查询新闻详情", response = UnifiedResponse.class)
    public UnifiedResponse queryArticleDetail(Integer id) throws Exception {
        return new UnifiedResponse(articleService.queryArticleDetail(id));
    }
}
