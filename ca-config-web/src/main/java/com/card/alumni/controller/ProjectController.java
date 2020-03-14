package com.card.alumni.controller;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResult;
import com.card.alumni.exception.CaException;
import com.card.alumni.model.ProjectModel;
import com.card.alumni.request.ProjectQueryRequest;
import com.card.alumni.request.ProjectRequest;
import com.card.alumni.service.ProjectService;
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
 * @date 2019-10-04 2:26 PM
 */
@RestController
@Api(value = "项目模块", tags = "项目模块")
@RequestMapping("/api/project")
public class ProjectController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

    private static final String LOGGER_PREFIX = "【项目模块】";

    @Autowired
    private ProjectService projectService;

    @PostMapping
    @ApiOperation(value = "保存项目", notes = "保存项目")
    public UnifiedResult<Integer> save(@RequestBody ProjectRequest request) throws Exception {

        LOGGER.info("{} save project. request = {}, operator = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        try {
            return UnifiedResult.success(projectService.save(request));
        } catch (CaException e) {
            LOGGER.error("{} save project error. request = {}", LOGGER_PREFIX, request, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
    }

    @PutMapping
    @ApiOperation(value = "更新项目", notes = "更新项目")
    public UnifiedResult update(@RequestBody ProjectRequest request) throws Exception {

        LOGGER.info("{} update project. request = {}, operator = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        try {
            projectService.update(request);
        } catch (CaException e) {
            LOGGER.error("{} update project error. request = {}", LOGGER_PREFIX, request, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
        return UnifiedResult.success();
    }

    @PutMapping("/{id}/view-count/{viewCount}")
    @ApiOperation(value = "更新项目", notes = "更新项目")
    public UnifiedResult updateViewCount(@PathVariable("id") Integer id,
                                         @PathVariable("id") Long viewCount) throws Exception {

        LOGGER.info("{} update project view count. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());
        try {
            projectService.updateViewCount(id, viewCount);
        } catch (CaException e) {
            LOGGER.error("{} update project view count error. id = {}", LOGGER_PREFIX, id, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
        return UnifiedResult.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据ID删除项目", notes = "根据ID删除项目")
    public UnifiedResult deleteById(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} delete project by id. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());
        projectService.deleteById(id);
        return UnifiedResult.success();
    }

    @PutMapping("/publish/{id}")
    @ApiOperation(value = "发布项目", notes = "发布项目")
    public UnifiedResult publish(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} publish project. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());
        try {
            projectService.publish(id);
        } catch (CaException e) {
            LOGGER.error("{} publish project error. id = {}", LOGGER_PREFIX, id, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
        return UnifiedResult.success();
    }

    @PutMapping("/retract/{id}")
    @ApiOperation(value = "取消发布项目", notes = "取消发布项目")
    public UnifiedResult<Integer> retract(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} retract project. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());
        try {
            projectService.unPublish(id);
        } catch (CaException e) {
            LOGGER.error("{} retract project error. id = {}", LOGGER_PREFIX, id, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
        return UnifiedResult.success();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询项目", notes = "根据ID查询项目")
    public UnifiedResult<ProjectModel> findById(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} find project by id. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());

        return UnifiedResult.success(projectService.findModelById(id));

    }

    @PostMapping("/page")
    @ApiOperation(value = "分页查询项目列表", notes = "分页查询项目列表")
    public UnifiedResult<PageData<ProjectModel>> pageByRequest(@RequestBody ProjectQueryRequest request) throws Exception {

        LOGGER.info("{} page projects by request. request = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        return UnifiedResult.success(projectService.pageByRequest(request));
    }

}
