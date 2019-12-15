package com.card.alumni.controller;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.request.RoleQueryRequest;
import com.card.alumni.request.RoleRequest;
import com.card.alumni.service.RoleService;
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
@Api(value = "角色模块", tags = "角色模块")
@RestController
@RequestMapping("/api/role")
public class RoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    private static final String LOGGER_PREFIX = "【角色模块】";

    @Autowired
    private RoleService roleService;

    @PostMapping
    @ApiOperation(value = "保存角色", notes = "保存角色", response = UnifiedResponse.class)
    public UnifiedResponse save(@RequestBody RoleRequest request) throws Exception {

        LOGGER.info("{} save role. request = {}, operator = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        try {
            return new UnifiedResponse(roleService.save(request));
        } catch (CaConfigException e) {
            LOGGER.error("{} save role error. request = {}", LOGGER_PREFIX, request, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
    }

    @PutMapping
    @ApiOperation(value = "更新角色", notes = "更新角色", response = UnifiedResponse.class)
    public UnifiedResponse update(@RequestBody RoleRequest request) throws Exception {

        LOGGER.info("{} update role. request = {}, operator = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        try {
            roleService.update(request);
        } catch (CaConfigException e) {
            LOGGER.error("{} update role error. request = {}", LOGGER_PREFIX, request, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据ID删除角色(刘明宇)", notes = "根据ID删除角色", response = UnifiedResponse.class)
    public UnifiedResponse deleteById(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} delete role by id. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());
        try {
            roleService.deleteById(id);
            return new UnifiedResponse();
        } catch (CaConfigException e) {
            LOGGER.error("{} delete role by id error. id = {}", LOGGER_PREFIX, id, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询角色", notes = "根据ID查询角色", response = UnifiedResponse.class)
    public UnifiedResponse findById(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} find role by id. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());

        return new UnifiedResponse(roleService.findModelById(id));

    }

    @PostMapping("/page")
    @ApiOperation(value = "分页查询角色列表", notes = "分页查询角色列表", response = UnifiedResponse.class)
    public UnifiedResponse pageByRequest(@RequestBody RoleQueryRequest request) throws Exception {

        LOGGER.info("{} page roles by request. request = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());

        return new UnifiedResponse(roleService.pageByRequest(request));
    }

}
