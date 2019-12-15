package com.card.alumni.controller;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.request.MenuRequest;
import com.card.alumni.service.MenuService;
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
 * @date 2019-10-04 2:26 PM
 */
@RestController
@Api(value = "菜单模块", tags = "菜单模块")
@RequestMapping("/api/menu")
public class MenuController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);

    private static final String LOGGER_PREFIX = "【菜单模块】";

    @Autowired
    private MenuService menuService;

    @PostMapping
    @ApiOperation(value = "保存菜单", notes = "保存菜单", response = UnifiedResponse.class)
    public UnifiedResponse save(@RequestBody MenuRequest request) throws Exception {

        LOGGER.info("{} save menu. request = {}, operator = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        try {
            return new UnifiedResponse(menuService.save(request));
        } catch (CaConfigException e) {
            LOGGER.error("{} save menu error. request = {}", LOGGER_PREFIX, request, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
    }

    @PutMapping
    @ApiOperation(value = "更新菜单", notes = "更新菜单", response = UnifiedResponse.class)
    public UnifiedResponse update(@RequestBody MenuRequest request) throws Exception {

        LOGGER.info("{} update menu. request = {}, operator = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        try {
            menuService.update(request);
        } catch (CaConfigException e) {
            LOGGER.error("{} update menu error. request = {}", LOGGER_PREFIX, request, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    @PutMapping("/{parentId}/rank")
    @ApiOperation(value = "按父ID排序", notes = "按父ID排序", response = UnifiedResponse.class)
    public UnifiedResponse rankByParentId(@PathVariable("parentId") Integer parentId, @RequestBody List<Integer> children) throws Exception {

        LOGGER.info("{} update menu. parentId = {}, children = {}, operator = {}", LOGGER_PREFIX, parentId, children, RequestUtil.getUserId().toString());
        try {
            menuService.rankByParentId(parentId, children);
        } catch (CaConfigException e) {
            LOGGER.error("{} update menu error. parentId = {}, children = {}", LOGGER_PREFIX, parentId, children, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
        return new UnifiedResponse();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据ID删除菜单(刘明宇)", notes = "根据ID删除菜单", response = UnifiedResponse.class)
    public UnifiedResponse deleteById(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} delete menu by id. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());
        try {
            menuService.deleteById(id);
            return new UnifiedResponse();
        } catch (CaConfigException e) {
            LOGGER.error("{} delete menu by id error. id = {}", LOGGER_PREFIX, id, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询菜单", notes = "根据ID查询菜单", response = UnifiedResponse.class)
    public UnifiedResponse findById(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} find menu by id. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());

        return new UnifiedResponse(menuService.findModelById(id));
    }

    @GetMapping("/{parentId}/children")
    @ApiOperation(value = "根据父ID查询子菜单列表", notes = "根据父ID查询子菜单列表", response = UnifiedResponse.class)
    public UnifiedResponse listRankByParentId(@PathVariable("parentId") Integer parentId) throws Exception {

        LOGGER.info("{} list rank menus by parentId. parentId = {}", LOGGER_PREFIX, parentId, RequestUtil.getUserId().toString());

        return new UnifiedResponse(menuService.listRankModelByParentId(parentId));
    }
}
