package com.card.alumni.controller;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.service.AlumniService;
import com.card.alumni.vo.query.AlumniQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 11:30 PM
 */
@RestController
@RequestMapping("/alumni")
public class AlumniController {

    @Resource
    private AlumniService alumniService;

    /**
     * 查询协会校友会
     * @param alumniQuery
     * @return
     */
    @RequestMapping("/page")
    public UnifiedResponse queryAlumniService(AlumniQuery alumniQuery) {
        return alumniService.queryAlumniService(alumniQuery);
    }
}
