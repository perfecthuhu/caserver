package com.card.alumni.controller;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.exception.CaException;
import com.card.alumni.service.RecommendService;
import com.card.alumni.utils.RequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liumingyu
 * @date 2019-12-21 10:56 PM
 */
@RestController
@RequestMapping("/api/recommend")
@Api(value = "推荐模块", tags = "推荐模块")
public class RecommendController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecommendController.class);

    private static final String LOGGER_PREFIX = "【推荐模块】";

    @Autowired
    private RecommendService recommendService;

    @GetMapping
    @ApiOperation(value = "推荐N个用户", notes = "推荐N个用户", response = UnifiedResponse.class)
    public UnifiedResponse recommend(@RequestParam(value = "size") Integer size) throws Exception {

        LOGGER.info("{} recommend users. size = {}, operator = {}", LOGGER_PREFIX, size, RequestUtil.getUserId().toString());
        try {
            return new UnifiedResponse(recommendService.recommend(size));
        } catch (CaException e) {
            LOGGER.error("{} recommend users error. size = {}", LOGGER_PREFIX, size, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
    }
}
