package com.card.alumni.controller;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResult;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.SchoolModel;
import com.card.alumni.request.SchoolQueryRequest;
import com.card.alumni.request.SchoolRequest;
import com.card.alumni.service.SchoolService;
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
@Api(value = "学校模块", tags = "学校模块")
@RestController
@RequestMapping("/api/school")
public class SchoolController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchoolController.class);

    private static final String LOGGER_PREFIX = "【学校模块】";

    @Autowired
    private SchoolService schoolService;

    @PostMapping
    @ApiOperation(value = "保存学校", notes = "保护学校")
    public UnifiedResult<Integer> save(@RequestBody SchoolRequest request) throws Exception {

        LOGGER.info("{} save school. request = {}, operator = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        try {
            return UnifiedResult.success(schoolService.save(request));
        } catch (CaConfigException e) {
            LOGGER.error("{} save school error. request = {}", LOGGER_PREFIX, request, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
    }

    @PutMapping
    @ApiOperation(value = "更新学校", notes = "更新学校")
    public UnifiedResult update(@RequestBody SchoolRequest request) throws Exception {

        LOGGER.info("{} update school. request = {}, operator = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        try {
            schoolService.update(request);
        } catch (CaConfigException e) {
            LOGGER.error("{} update school error. request = {}", LOGGER_PREFIX, request, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
        return UnifiedResult.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据ID删除学校", notes = "根据ID删除学校")
    public UnifiedResult deleteById(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} delete school by id. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());
        try {
            schoolService.deleteById(id);
            return UnifiedResult.success();
        } catch (CaConfigException e) {
            LOGGER.error("{} delete school by id error. id = {}", LOGGER_PREFIX, id, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询学校", notes = "根据ID查询学校")
    public UnifiedResult<SchoolModel> findById(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} find school by id. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());

        return UnifiedResult.success(schoolService.findModelById(id));

    }

    @PostMapping("/page")
    @ApiOperation(value = "分页查询学校列表", notes = "分页查询学校列表")
    public UnifiedResult<PageData<SchoolModel>> pageByRequest(@RequestBody SchoolQueryRequest request) throws Exception {

        LOGGER.info("{} page schools by request. request = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());

        return UnifiedResult.success(schoolService.pageByRequest(request));
    }

}
