package com.card.alumni.controller;

import com.card.alumni.common.UnifiedResult;
import com.card.alumni.exception.CaException;
import com.card.alumni.service.BaseInfoService;
import com.card.alumni.vo.HomeVO;
import com.card.alumni.vo.SchoolVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

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
     *
     * @return
     */
    @GetMapping("/school")
    @ApiOperation(value = "查询所有学校信息", notes = "查询所有学校信息")
    public UnifiedResult<List<SchoolVO>> querySchool() {
        return UnifiedResult.success(baseInfoService.querySchool());
    }

    /**
     * 查询首页结构信息
     *
     * @return
     */
    @GetMapping("/home")
    @ApiOperation(value = "查询首页结构信息", notes = "查询首页结构信息")
    public UnifiedResult<HomeVO> queryHomeInfo() {
        return UnifiedResult.success(baseInfoService.queryHomeInfo());
    }

    @GetMapping("/upload")
    @ApiOperation(value = "上传文件及图片", notes = "上传文件及图片")
    public UnifiedResult<String> uploadFile(@RequestParam("file") MultipartFile file, Long maxSize) throws CaException {
        return UnifiedResult.success(baseInfoService.uploadFile(file, maxSize));
    }
}
