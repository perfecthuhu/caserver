package com.card.alumni.controller;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResult;
import com.card.alumni.service.ArticleService;
import com.card.alumni.vo.ArticleVO;
import com.card.alumni.vo.query.ArticleQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
     *
     * @return
     */
    @PostMapping("/page")
    @ApiOperation(value = "查询首页新闻列表", notes = "查询首页新闻列表")
    public UnifiedResult<Map<Integer, List<ArticleVO>>> queryArticleService() throws Exception {
        return UnifiedResult.success(articleService.queryArticleService());
    }

    @PostMapping("/queryPage")
    @ApiOperation(value = "查询新闻列表", notes = "查询新闻列表")
    public UnifiedResult<PageData<ArticleVO>> queryPage(@RequestBody ArticleQuery articleQuery) throws Exception {
        return UnifiedResult.success(articleService.queryPage(articleQuery));
    }

    /**
     * 查询新闻详情
     *
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    @ApiOperation(value = "查询新闻详情", notes = "查询新闻详情")
    public UnifiedResult<ArticleVO> queryArticleDetail(@PathVariable Integer id) throws Exception {
        return UnifiedResult.success(articleService.queryArticleDetail(id));
    }
}
