package com.card.alumni.controller;

import com.card.alumni.common.UnifiedResult;
import com.card.alumni.request.LikeRequest;
import com.card.alumni.service.LikeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author liumingyu
 * @date 2020-03-18 6:46 PM
 */
@RestController
@RequestMapping("/api/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping
    @ApiOperation(value = "执行点赞", notes = "执行点赞", response = UnifiedResult.class)
    public UnifiedResult<Long> save(@RequestBody LikeRequest request) {
        return UnifiedResult.success(likeService.like(request));
    }

    @PutMapping
    @ApiOperation(value = "取消点赞", notes = "取消点赞", response = UnifiedResult.class)
    public UnifiedResult cancel(@RequestBody LikeRequest request) {
        likeService.cancel(request);
        return UnifiedResult.success();
    }

    @GetMapping("/tenant/{tenantId}/{targetId}/count")
    @ApiOperation(value = "查询点赞数量", notes = "查询点赞数量", response = UnifiedResult.class)
    public UnifiedResult<Integer> countByTenantIdAndTargetId(@PathVariable("tenantId") Integer tenantId,
                                                             @PathVariable("targetId") Long targetId) {
        return UnifiedResult.success(likeService.countByTenantIdAndTargetId(tenantId, targetId));
    }

    @GetMapping("/tenant/{tenantId}/counts")
    @ApiOperation(value = "批量查询点赞数量", notes = "批量查询点赞数量", response = UnifiedResult.class)
    public UnifiedResult<Map<Long, Integer>> mapCountByTenantIdAndTargetId(@PathVariable("tenantId") Integer tenantId,
                                                                           @RequestParam List<Long> targetIdList) {
        return UnifiedResult.success(likeService.mapCountByTenantIdAndTargetIds(tenantId, targetIdList));
    }
}
