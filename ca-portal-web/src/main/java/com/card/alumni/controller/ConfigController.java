package com.card.alumni.controller;

import com.card.alumni.common.UnifiedResult;
import com.card.alumni.model.ConfigModel;
import com.card.alumni.service.ConfigService;
import com.card.alumni.utils.RequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liumingyu
 * @date 2020-01-19 11:34 AM
 */
@RestController
@RequestMapping("/api/config")
@Api(value = "配置模块", tags = "配置模块")
public class ConfigController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigController.class);

    private static final String LOGGER_PREFIX = "【配置模块】";

    @Autowired
    private ConfigService configService;

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询配置", notes = "根据ID查询配置")
    public UnifiedResult<ConfigModel> findById(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} find config by id. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());

        return UnifiedResult.success(configService.findModelById(id));
    }

    @GetMapping("/key/{key}")
    @ApiOperation(value = "根据key查询配置", notes = "根据key查询配置")
    public UnifiedResult<ConfigModel> findByKey(@PathVariable("key") String key) throws Exception {

        LOGGER.info("{} find config by key. key = {}, operator = {}", LOGGER_PREFIX, key, RequestUtil.getUserId().toString());

        return UnifiedResult.success(configService.findModelByKey(key));
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询配置列表(为空时查全部)", notes = "查询配置列表(为空时查全部)")
    public UnifiedResult<List<ConfigModel>> listByKey(@RequestParam(value = "key", required = false, defaultValue = "") String key) throws Exception {

        LOGGER.info("{} list config by key. key = {}", LOGGER_PREFIX, key, RequestUtil.getUserId().toString());

        return UnifiedResult.success(configService.listModelByKey(key));
    }

}
