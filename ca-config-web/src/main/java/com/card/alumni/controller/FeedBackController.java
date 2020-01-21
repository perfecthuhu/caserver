package com.card.alumni.controller;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResult;
import com.card.alumni.model.FeedBackModel;
import com.card.alumni.request.FeedBackRequest;
import com.card.alumni.service.FeedBackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author sunxiaodong10 2020/1/21
 * @date 5:50 PM
 */
@RestController
@RequestMapping("/feedback")
@Api(value = "用户反馈模块", tags = "用户反馈模块")
public class FeedBackController {

    @Resource
    private FeedBackService feedBackService;

    /**
     * 分页查询问题反馈
     * @param feedBackRequest
     * @return
     */
    @RequestMapping("/queryPage")
    @ApiOperation(value = "分页查询用户反馈", notes = "分页查询用户反馈")
    public UnifiedResult<PageData<FeedBackModel>> queryPage(@RequestBody FeedBackRequest feedBackRequest) {
        return UnifiedResult.success(feedBackService.queryPage(feedBackRequest));
    }

    /**
     * 解决反馈问题（支持批量）
     * @param feedBackRequest
     */
    @RequestMapping("/update")
    @ApiOperation(value = "将反馈问题置为已解决", notes = "将反馈问题置为已解决")
    public UnifiedResult<Boolean> update(FeedBackRequest feedBackRequest) {
        feedBackService.update(feedBackRequest);
        return UnifiedResult.success();
    }
}
