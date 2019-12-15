package com.card.alumni.controller;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.service.BaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 11:12 PM
 */
@RestController
@RequestMapping("/base/config")
@Api(value = "新闻模块", tags = "新闻模块")
public class BaseInfoController {

    @Resource
    private BaseInfoService baseInfoService;

    /**
     * 查询所有学校信息
     * @return
     */
    @GetMapping("/school")
    @ApiOperation(value = "查询所有学校信息", notes = "查询所有学校信息", response = UnifiedResponse.class)
    public UnifiedResponse querySchool() {
        return new UnifiedResponse(baseInfoService.querySchool());
    }

    /**
     * 查询首页结构信息
     * @return
     */
    @GetMapping("/home")
    @ApiOperation(value = "查询首页结构信息", notes = "查询首页结构信息", response = UnifiedResponse.class)
    public UnifiedResponse queryHomeInfo() {
        return new UnifiedResponse(baseInfoService.queryHomeInfo());
    }
}
