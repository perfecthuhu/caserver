package com.card.alumni.controller;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResult;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.HomePageModel;
import com.card.alumni.request.HomePageQueryRequest;
import com.card.alumni.request.HomePageRequest;
import com.card.alumni.service.HomePageService;
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

/**
 * @author liumingyu
 * @date 2019-12-09 9:55 PM
 */
@Api(value = "主页模块", tags = "主页模块")
@RestController
@RequestMapping("/api/home/page")
public class HomePageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomePageController.class);

    private static final String LOGGER_PREFIX = "【主页模块】";

    @Autowired
    private HomePageService homePageService;

    @PostMapping
    @ApiOperation(value = "保存主页", notes = "保存主页")
    public UnifiedResult save(@RequestBody HomePageRequest request) throws Exception {

        LOGGER.info("{} save home page. request = {}, operator = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        try {
            return UnifiedResult.success(homePageService.save(request));
        } catch (CaConfigException e) {
            LOGGER.error("{} save home page error. request = {}", LOGGER_PREFIX, request, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
    }

    @PutMapping
    @ApiOperation(value = "更新主页", notes = "更新主页")
    public UnifiedResult update(@RequestBody HomePageRequest request) throws Exception {

        LOGGER.info("{} update home page. request = {}, operator = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        try {
            homePageService.update(request);
        } catch (CaConfigException e) {
            LOGGER.error("{} update home page error. request = {}", LOGGER_PREFIX, request, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
        return UnifiedResult.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据ID删除主页", notes = "根据ID删除主页")
    public UnifiedResult deleteById(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} delete home page by id. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());
        try {
            homePageService.deleteById(id);
            return UnifiedResult.success();
        } catch (CaConfigException e) {
            LOGGER.error("{} delete home page by id error. id = {}", LOGGER_PREFIX, id, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询主页", notes = "根据ID查询主页")
    public UnifiedResult<HomePageModel> findById(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} find home page by id. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());

        return UnifiedResult.success(homePageService.findModelById(id));

    }

    @PostMapping("/page")
    @ApiOperation(value = "分页查询主页列表", notes = "分页查询主页列表")
    public UnifiedResult<PageData<HomePageModel>> pageByRequest(@RequestBody HomePageQueryRequest request) throws Exception {

        LOGGER.info("{} page home pages by request. request = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());

        return UnifiedResult.success(homePageService.pageByRequest(request));
    }

}
