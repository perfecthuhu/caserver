package com.card.alumni.controller;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResult;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.HomeGuideModel;
import com.card.alumni.request.HomeGuideQueryRequest;
import com.card.alumni.request.HomeGuideRequest;
import com.card.alumni.service.HomeGuideService;
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

import java.util.List;

/**
 * @author liumingyu
 * @date 2019-12-09 9:55 PM
 */
@Api(value = "主页导航模块", tags = "主页导航模块")
@RestController
@RequestMapping("/api/home/guide")
public class HomeGuideController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeGuideController.class);

    private static final String LOGGER_PREFIX = "【主页导航模块】";

    @Autowired
    private HomeGuideService homeGuideService;

    @PostMapping
    @ApiOperation(value = "保存主页导航", notes = "保存主页导航")
    public UnifiedResult<Integer> save(@RequestBody HomeGuideRequest request) throws Exception {

        LOGGER.info("{} save home guide. request = {}, operator = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        try {
            return UnifiedResult.success(homeGuideService.save(request));
        } catch (CaConfigException e) {
            LOGGER.error("{} save home guide error. request = {}", LOGGER_PREFIX, request, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
    }

    @PutMapping
    @ApiOperation(value = "更新主页导航", notes = "更新主页导航")
    public UnifiedResult update(@RequestBody HomeGuideRequest request) throws Exception {

        LOGGER.info("{} update home guide. request = {}, operator = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        try {
            homeGuideService.update(request);
        } catch (CaConfigException e) {
            LOGGER.error("{} update home guide error. request = {}", LOGGER_PREFIX, request, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
        return UnifiedResult.success();
    }

    @PutMapping("/{pageHomeId}/rank")
    @ApiOperation(value = "更新主页导航", notes = "更新主页导航")
    public UnifiedResult rank(@PathVariable("pageHomeId") Integer pageHomeId, @RequestBody List<Integer> children) throws Exception {

        LOGGER.info("{} update home guide. pageHomeId = {}, children = {}, operator = {}", LOGGER_PREFIX, pageHomeId, children, RequestUtil.getUserId().toString());
        try {
            homeGuideService.rank(pageHomeId, children);
        } catch (CaConfigException e) {
            LOGGER.error("{} update home guide error. pageHomeId = {}, request = {}", LOGGER_PREFIX, pageHomeId, children, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
        return UnifiedResult.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据ID删除主页导航", notes = "根据ID删除主页导航")
    public UnifiedResult deleteById(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} delete home guide by id. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());
        try {
            homeGuideService.deleteById(id);
            return UnifiedResult.success();
        } catch (CaConfigException e) {
            LOGGER.error("{} delete home guide by id error. id = {}", LOGGER_PREFIX, id, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询主页导航", notes = "根据ID查询主页导航")
    public UnifiedResult<HomeGuideModel> findById(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} find home guide by id. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());

        return UnifiedResult.success(homeGuideService.findModelById(id));

    }

    @GetMapping("/{homePageId}/list")
    @ApiOperation(value = "根据主页ID查询有序导航列表", notes = "根据主页ID查询有序导航列表")
    public UnifiedResult<List<HomeGuideModel>> listRankByHomePageId(@PathVariable("homePageId") Integer homePageId) throws Exception {

        LOGGER.info("{} list home guides by homePageId. homePageId = {}", LOGGER_PREFIX, homePageId, RequestUtil.getUserId().toString());

        return UnifiedResult.success(homeGuideService.listRankModelByHomePageId(homePageId));
    }

    @PostMapping("/page")
    @ApiOperation(value = "分页查询主页导航列表", notes = "分页查询主页导航列表")
    public UnifiedResult<PageData<HomeGuideModel>> pageByRequest(@RequestBody HomeGuideQueryRequest request) throws Exception {

        LOGGER.info("{} page home guides by request. request = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());

        return UnifiedResult.success(homeGuideService.pageByRequest(request));
    }

}
