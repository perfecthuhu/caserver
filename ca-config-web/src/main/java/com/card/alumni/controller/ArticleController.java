package com.card.alumni.controller;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.request.ArticleQueryRequest;
import com.card.alumni.request.ArticleRequest;
import com.card.alumni.service.ArticleService;
import com.card.alumni.utils.RequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liumingyu
 * @date 2019-10-04 2:26 PM
 */
@RestController
@Api(value = "文章模块", tags = "文章模块")
@RequestMapping("/api/article")
public class ArticleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

    private static final String LOGGER_PREFIX = "【文章模块】";

    @Autowired
    private ArticleService articleService;

    @PostMapping
    @ApiOperation(value = "保存文章", notes = "保存文章", response = UnifiedResponse.class)
    public UnifiedResponse save(@RequestBody ArticleRequest articleRequest, HttpServletRequest request) throws Exception {

        LOGGER.info("{} save article. request = {}, operator = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        try {
            return new UnifiedResponse(articleService.save(articleRequest));
        } catch (CaConfigException e) {
            LOGGER.error("{} save article error. request = {}", LOGGER_PREFIX, request, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
    }

    @PutMapping
    @ApiOperation(value = "更新文章", notes = "更新文章", response = UnifiedResponse.class)
    public UnifiedResponse update(@RequestBody ArticleRequest articleRequest, HttpServletRequest request) throws Exception {

        LOGGER.info("{} update article. request = {}, operator = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        try {
            articleService.update(articleRequest);
        } catch (CaConfigException e) {
            LOGGER.error("{} update article error. request = {}", LOGGER_PREFIX, request, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据ID删除文章", notes = "根据ID删除文章", response = UnifiedResponse.class)
    public UnifiedResponse deleteById(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} delete article by id. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());
        try {
            articleService.deleteById(id);
            return new UnifiedResponse();
        } catch (CaConfigException e) {
            LOGGER.error("{} delete article by id error. id = {}", LOGGER_PREFIX, id, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询文章", notes = "根据ID查询文章", response = UnifiedResponse.class)
    public UnifiedResponse findById(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} find article by id. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());

        return new UnifiedResponse(articleService.findModelById(id));

    }

    @PostMapping("/page")
    @ApiOperation(value = "分页查询文章列表", notes = "分页查询文章列表", response = UnifiedResponse.class)
    public UnifiedResponse pageByRequest(@RequestBody ArticleQueryRequest request) throws Exception {

        LOGGER.info("{} page articles by request. request = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        return new UnifiedResponse(articleService.pageByRequest(request));
    }

}
