package com.card.alumni.controller;

import com.card.alumni.common.UnifiedResult;
import com.card.alumni.exception.CaException;
import com.card.alumni.model.ConfigModel;
import com.card.alumni.request.ConfigRequest;
import com.card.alumni.service.ConfigService;
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

    @PostMapping
    @ApiOperation(value = "创建配置", notes = "创建配置")
    public UnifiedResult<Integer> save(@RequestBody ConfigRequest request) throws Exception {

        LOGGER.info("{} save config. request = {}, operator = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        try {
            return UnifiedResult.success(configService.save(request));
        } catch (CaException e) {
            LOGGER.error("{} save config error. request = {}", LOGGER_PREFIX, request, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
    }

    @PutMapping
    @ApiOperation(value = "更新配置", notes = "更新配置")
    public UnifiedResult update(@RequestBody ConfigRequest request) throws Exception {

        LOGGER.info("{} update config. request = {}, operator = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        try {
            configService.update(request);
        } catch (CaException e) {
            LOGGER.error("{} update config error. request = {}", LOGGER_PREFIX, request, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
        return UnifiedResult.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据ID删除配置", notes = "根据ID删除配置")
    public UnifiedResult deleteById(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} delete config by id. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());
        try {
            configService.deleteById(id);
        } catch (CaException e) {
            LOGGER.error("{} delete config by id error. id = {}", LOGGER_PREFIX, id, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
        return UnifiedResult.success();
    }

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
