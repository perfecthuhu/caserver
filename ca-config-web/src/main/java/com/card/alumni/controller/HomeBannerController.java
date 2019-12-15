package com.card.alumni.controller;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.request.HomeBannerQueryRequest;
import com.card.alumni.request.HomeBannerRequest;
import com.card.alumni.service.HomeBannerService;
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
@Api(value = "主页轮播图模块", tags = "主页轮播图模块")
@RestController
@RequestMapping("/api/home/banner")
public class HomeBannerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeBannerController.class);

    private static final String LOGGER_PREFIX = "【主页轮播图模块】";

    @Autowired
    private HomeBannerService homeBannerService;

    @PostMapping
    @ApiOperation(value = "保存主页轮播图", notes = "保存主页轮播图", response = UnifiedResponse.class)
    public UnifiedResponse save(@RequestBody HomeBannerRequest request) throws Exception {

        LOGGER.info("{} save home banner. request = {}, operator = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        try {
            return new UnifiedResponse(homeBannerService.save(request));
        } catch (CaConfigException e) {
            LOGGER.error("{} save home banner error. request = {}", LOGGER_PREFIX, request, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
    }

    @PutMapping
    @ApiOperation(value = "更新主页轮播图", notes = "更新主页轮播图", response = UnifiedResponse.class)
    public UnifiedResponse update(@RequestBody HomeBannerRequest request) throws Exception {

        LOGGER.info("{} update home banner. request = {}, operator = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        try {
            homeBannerService.update(request);
        } catch (CaConfigException e) {
            LOGGER.error("{} update home banner error. request = {}", LOGGER_PREFIX, request, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    @PutMapping("/{pageHomeId}/rank")
    @ApiOperation(value = "排序主页轮播图", notes = "更新主页轮播图", response = UnifiedResponse.class)
    public UnifiedResponse rank(@PathVariable("pageHomeId") Integer pageHomeId, @RequestBody List<Integer> children) throws Exception {

        LOGGER.info("{} update home banner. pageHomeId = {}, children = {}, operator = {}", LOGGER_PREFIX, pageHomeId, children, RequestUtil.getUserId().toString());
        try {
            homeBannerService.rank(pageHomeId, children);
        } catch (CaConfigException e) {
            LOGGER.error("{} update home banner error. pageHomeId = {}, request = {}", LOGGER_PREFIX, pageHomeId, children, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据ID删除主页轮播图", notes = "根据ID删除主页轮播图", response = UnifiedResponse.class)
    public UnifiedResponse deleteById(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} delete home banner by id. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());
        try {
            homeBannerService.deleteById(id);
            return new UnifiedResponse();
        } catch (CaConfigException e) {
            LOGGER.error("{} delete home banner by id error. id = {}", LOGGER_PREFIX, id, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询主页轮播图", notes = "根据ID查询主页轮播图", response = UnifiedResponse.class)
    public UnifiedResponse findById(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} find home banner by id. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());

        return new UnifiedResponse(homeBannerService.findModelById(id));

    }

    @GetMapping("/{homePageId}/list")
    @ApiOperation(value = "根据主页ID查询有序轮播图列表", notes = "根据主页ID查询有序轮播图列表", response = UnifiedResponse.class)
    public UnifiedResponse listRankByHomePageId(@PathVariable("homePageId") Integer homePageId) throws Exception {

        LOGGER.info("{} list home banners by homePageId. homePageId = {}", LOGGER_PREFIX, homePageId, RequestUtil.getUserId().toString());

        return new UnifiedResponse(homeBannerService.listRankModelByHomePageId(homePageId));
    }

    @PostMapping("/page")
    @ApiOperation(value = "查询主页轮播图列表", notes = "查询主页轮播图列表", response = UnifiedResponse.class)
    public UnifiedResponse listByRequest(@RequestBody HomeBannerQueryRequest request) throws Exception {

        LOGGER.info("{} page home banners by request. request = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());

        return new UnifiedResponse(homeBannerService.listRankModelByRequest(request));
    }

}
