package com.card.alumni.controller;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.service.BaseInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 11:12 PM
 */
@RestController
@RequestMapping("/base/config")
public class BaseInfoController {

    @Resource
    private BaseInfoService baseInfoService;

    /**
     * 查询所有学校信息
     * @return
     */
    @RequestMapping("/school")
    public UnifiedResponse querySchool() {
        return baseInfoService.querySchool();
    }

    /**
     * 查询首页结构信息
     * @return
     */
    @RequestMapping("/home")
    public UnifiedResponse queryHomeInfo() {
        return baseInfoService.queryHomeInfo();
    }
}
