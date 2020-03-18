package com.card.alumni.controller;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResult;
import com.card.alumni.model.CommentModel;
import com.card.alumni.request.CommentQueryRequest;
import com.card.alumni.request.CommentRequest;
import com.card.alumni.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liumingyu
 * @date 2020-03-18 6:46 PM
 */
@RestController
@RequestMapping("/api/comment")
@Api(value = "评论模块", tags = "评论模块")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    @ApiOperation(value = "执行评论", notes = "执行评论", response = UnifiedResult.class)
    public UnifiedResult<Long> save(@RequestBody CommentRequest request) {
        return UnifiedResult.success(commentService.save(request));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除评论", notes = "删除评论", response = UnifiedResult.class)
    public UnifiedResult deleteById(@PathVariable("id") Long id) {
        commentService.deleteById(id);
        return UnifiedResult.success();
    }

    @GetMapping("/tenant/{tenantId}/{targetId}")
    @ApiOperation(value = "查询评论数量", notes = "查询评论数量", response = UnifiedResult.class)
    public UnifiedResult<Integer> countByTenantAndTargetId(@PathVariable("tenantId") Integer tenantId,
                                                           @PathVariable("targetId") Long targetId) {
        return UnifiedResult.success(commentService.countByTenantAndTargetId(tenantId, targetId));
    }

    @PostMapping("/page")
    @ApiOperation(value = "分页查询评论", notes = "分页查询评论", response = UnifiedResult.class)
    public UnifiedResult<PageData<CommentModel>> pageByRequest(@RequestBody CommentQueryRequest request) {
        return UnifiedResult.success(commentService.pageByRequest(request));
    }
}
