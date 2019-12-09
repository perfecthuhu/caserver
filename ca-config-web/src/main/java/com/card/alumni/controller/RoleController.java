package com.card.alumni.controller;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.request.RoleQueryRequest;
import com.card.alumni.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
    @ApiOperation(value = "分页查询角色列表", notes = "分页查询角色列表", response = UnifiedResponse.class)
    public UnifiedResponse pageByRequest(@RequestBody RoleQueryRequest request) throws Exception {

        return new UnifiedResponse(roleService.pageByKeyword(request));
    }
}
