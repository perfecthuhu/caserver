package com.card.alumni.controller;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResult;
import com.card.alumni.model.FeedBackModel;
import com.card.alumni.request.FeedBackQueryRequest;
import com.card.alumni.request.FeedBackRequest;
import com.card.alumni.service.FeedBackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author sunxiaodong10 2020/1/21
 * @date 5:50 PM
 */
@RestController
@RequestMapping("/api/feedback")
@Api(value = "用户反馈模块", tags = "用户反馈模块")
public class FeedBackController {

    @Resource
    private FeedBackService feedBackService;

    @PostMapping("/page")
    @ApiOperation(value = "分页查询用户反馈", notes = "分页查询用户反馈")
    public UnifiedResult<PageData<FeedBackModel>> queryPage(@RequestBody FeedBackQueryRequest feedBackRequest) {
        return UnifiedResult.success(feedBackService.queryPage(feedBackRequest));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询用户反馈", notes = "根据ID查询用户反馈")
    public UnifiedResult<FeedBackModel> findById(@PathVariable("id") Integer id) {
        return UnifiedResult.success(feedBackService.findModeById(id));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "单个解决", notes = "单个解决")
    public UnifiedResult<Boolean> resolve(@PathVariable("id") Integer id) {
        feedBackService.resolve(id);
        return UnifiedResult.success();
    }

    @PutMapping("/batch")
    @ApiOperation(value = "批量解决", notes = "批量解决")
    public UnifiedResult<Boolean> batchResolve(FeedBackRequest feedBackRequest) {
        feedBackService.batchResolve(feedBackRequest);
        return UnifiedResult.success();
    }
}
