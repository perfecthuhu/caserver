package com.card.alumni.controller;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResult;
import com.card.alumni.model.FeedBackModel;
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

    /**
     * 分页查询问题反馈
     * @param feedBackRequest
     * @return
     */
    @PostMapping("/page")
    @ApiOperation(value = "分页查询用户反馈", notes = "分页查询用户反馈")
    public UnifiedResult<PageData<FeedBackModel>> queryPage(@RequestBody FeedBackRequest feedBackRequest) {
        return UnifiedResult.success(feedBackService.queryPage(feedBackRequest));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询用户反馈", notes = "根据ID查询用户反馈")
    public UnifiedResult<FeedBackModel> findById(@PathVariable("id") Integer id) {
        return UnifiedResult.success(feedBackService.findModeById(id));
    }

    /**
     * 解决反馈问题（支持批量）
     * @param feedBackRequest
     */
    @PutMapping
    @ApiOperation(value = "将反馈问题置为已解决", notes = "将反馈问题置为已解决")
    public UnifiedResult<Boolean> update(FeedBackRequest feedBackRequest) {
        feedBackService.update(feedBackRequest);
        return UnifiedResult.success();
    }
}
