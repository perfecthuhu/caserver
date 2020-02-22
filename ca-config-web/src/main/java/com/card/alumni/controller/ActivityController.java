package com.card.alumni.controller;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResult;
import com.card.alumni.exception.CaException;
import com.card.alumni.model.ActivityModel;
import com.card.alumni.request.ActivityQueryRequest;
import com.card.alumni.request.ActivityRequest;
import com.card.alumni.service.ActivityService;
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
 * @date 2020-01-19 11:34 AM
 */
@RestController
@RequestMapping("/api/activity")
@Api(value = "活动模块", tags = "活动模块")
public class ActivityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityController.class);

    private static final String LOGGER_PREFIX = "【活动模块】";

    @Autowired
    private ActivityService activityService;

    @PostMapping
    @ApiOperation(value = "创建活动", notes = "创建活动")
    public UnifiedResult<Integer> save(@RequestBody ActivityRequest request) throws Exception {

        LOGGER.info("{} save activity. request = {}, operator = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        try {
            return UnifiedResult.success(activityService.save(request));
        } catch (CaException e) {
            LOGGER.error("{} save activity error. request = {}", LOGGER_PREFIX, request, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
    }

    @PostMapping("/onclick-publish")
    @ApiOperation(value = "创建并发布活动", notes = "创建并发布活动")
    public UnifiedResult<Integer> saveAndPublish(@RequestBody ActivityRequest request) throws Exception {

        LOGGER.info("{} save and publish activity. request = {}, operator = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        try {
            return UnifiedResult.success(activityService.saveAndPublish(request));
        } catch (CaException e) {
            LOGGER.error("{} save and publish activity error. request = {}", LOGGER_PREFIX, request, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
    }

    @PutMapping("/publish/{id}")
    @ApiOperation(value = "发布活动", notes = "发布活动")
    public UnifiedResult publish(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} publish activity. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());
        try {
            activityService.publish(id);
        } catch (CaException e) {
            LOGGER.error("{} publish activity error. id = {}", LOGGER_PREFIX, id, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
        return UnifiedResult.success();
    }

    @PutMapping("/retract/{id}")
    @ApiOperation(value = "撤回活动", notes = "撤回活动")
    public UnifiedResult<Integer> retract(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} retract activity. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());
        try {
            activityService.retract(id);
        } catch (CaException e) {
            LOGGER.error("{} retract activity error. id = {}", LOGGER_PREFIX, id, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
        return UnifiedResult.success();
    }

    @PutMapping
    @ApiOperation(value = "更新活动", notes = "更新活动")
    public UnifiedResult update(@RequestBody ActivityRequest request) throws Exception {

        LOGGER.info("{} update activity. request = {}, operator = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        try {
            activityService.update(request);
        } catch (CaException e) {
            LOGGER.error("{} update activity error. request = {}", LOGGER_PREFIX, request, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
        return UnifiedResult.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据ID删除活动", notes = "根据ID删除活动")
    public UnifiedResult deleteById(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} delete activity by id. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());
        try {
            activityService.deleteById(id);
        } catch (CaException e) {
            LOGGER.error("{} delete activity by id error. id = {}", LOGGER_PREFIX, id, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
        return UnifiedResult.success();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询活动", notes = "根据ID查询活动")
    public UnifiedResult<ActivityModel> findById(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} find activity by id. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());

        return UnifiedResult.success(activityService.findModelById(id));
    }

    @PostMapping("/page")
    @ApiOperation(value = "分页查询活动列表", notes = "分页查询活动列表")
    public UnifiedResult<PageData<ActivityModel>> pageByRequest(@RequestBody ActivityQueryRequest request) throws Exception {

        LOGGER.info("{} page activities by request. request = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());

        return UnifiedResult.success(activityService.pageByRequest(request));
    }
}
