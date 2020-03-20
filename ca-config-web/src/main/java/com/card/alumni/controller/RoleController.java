package com.card.alumni.controller;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResult;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.MenuModel;
import com.card.alumni.model.RoleModel;
import com.card.alumni.model.UserModel;
import com.card.alumni.request.RoleQueryRequest;
import com.card.alumni.request.RoleRequest;
import com.card.alumni.service.MenuService;
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

import java.util.List;

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

    @Autowired
    private MenuService menuService;

    @PostMapping
    @ApiOperation(value = "保存角色", notes = "保存角色")
    public UnifiedResult<Integer> save(@RequestBody RoleRequest request) throws Exception {

        LOGGER.info("{} save role. request = {}, operator = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        try {
            return UnifiedResult.success(roleService.save(request));
        } catch (CaConfigException e) {
            LOGGER.error("{} save role error. request = {}", LOGGER_PREFIX, request, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
    }

    @PutMapping
    @ApiOperation(value = "更新角色", notes = "更新角色")
    public UnifiedResult update(@RequestBody RoleRequest request) throws Exception {

        LOGGER.info("{} update role. request = {}, operator = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        try {
            roleService.update(request);
        } catch (CaConfigException e) {
            LOGGER.error("{} update role error. request = {}", LOGGER_PREFIX, request, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
        return UnifiedResult.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据ID删除角色", notes = "根据ID删除角色")
    public UnifiedResult deleteById(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} delete role by id. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());
        try {
            roleService.deleteById(id);
            return UnifiedResult.success();
        } catch (CaConfigException e) {
            LOGGER.error("{} delete role by id error. id = {}", LOGGER_PREFIX, id, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询角色", notes = "根据ID查询角色")
    public UnifiedResult<RoleModel> findById(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} find role by id. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());

        return UnifiedResult.success(roleService.findModelById(id));
    }

    @GetMapping("/{id}/users")
    @ApiOperation(value = "根据角色ID查询关联的用户列表", notes = "根据角色ID查询关联的用户列表")
    public UnifiedResult<List<UserModel>> listUserByRoleId(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} list user by role id. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());

        return UnifiedResult.success(roleService.listUserByRoleId(id));
    }

    @GetMapping("/all")
    @ApiOperation(value = "查询全部角色列表", notes = "查询全部角色列表")
    public UnifiedResult<List<RoleModel>> listAll() throws Exception {

        LOGGER.info("{} list all roles. operatorId = {}", LOGGER_PREFIX, RequestUtil.getUserId().toString());

        return UnifiedResult.success(roleService.listAll());
    }

    @PostMapping("/page")
    @ApiOperation(value = "分页查询角色列表(带关联菜单)", notes = "分页查询角色列表")
    public UnifiedResult<PageData<RoleModel>> pageByRequest(@RequestBody RoleQueryRequest request) throws Exception {

        LOGGER.info("{} page roles by request. request = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());

        return UnifiedResult.success(roleService.pageByRequest(request));
    }

    @PostMapping("/{id}/menus")
    @ApiOperation(value = "批量保存角色和菜单的关联关系", notes = "批量保存角色和菜单的关联关系")
    public UnifiedResult batchSaveRoleMenuRel(@PathVariable("id") Integer id, @RequestBody List<Integer> menuIdList) throws Exception {
        LOGGER.info("{} batch save role menu rel. roleId = {}, menuIdList = {}, operatorId = {}", LOGGER_PREFIX, id, menuIdList, RequestUtil.getUserId().toString());
        roleService.batchSaveRoleMenuRel(id, menuIdList);
        return UnifiedResult.success();
    }

    @GetMapping("/{id}/menus")
    @ApiOperation(value = "查询角色关联的菜单列表", notes = "查询角色关联的菜单列表")
    public UnifiedResult<List<MenuModel>> listModelsByRoleId(@PathVariable("id") Integer id) throws Exception {
        LOGGER.info("{} list menus by roleId. roleId = {}, operatorId = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());
        List<Integer> roleIds = roleService.listMenuIdsByRoleId(id);
        return UnifiedResult.success(menuService.listRankModelByIdList(roleIds));
    }
}
