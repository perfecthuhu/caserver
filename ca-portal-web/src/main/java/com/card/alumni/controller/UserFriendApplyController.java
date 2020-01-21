package com.card.alumni.controller;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResult;
import com.card.alumni.exception.CaException;
import com.card.alumni.model.UserFriendApplyModel;
import com.card.alumni.request.UserFriendApplyQueryRequest;
import com.card.alumni.request.UserFriendApplyRequest;
import com.card.alumni.service.UserFriendApplyService;
import com.card.alumni.utils.RequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liumingyu
 * @date 2019-10-04 2:26 PM
 */
@RestController
@Api(value = "好友申请模块", tags = "好友申请模块")
@RequestMapping("/api/user/friend/apply")
public class UserFriendApplyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserFriendApplyController.class);

    private static final String LOGGER_PREFIX = "【好友申请模块】";

    @Autowired
    private UserFriendApplyService userFriendApplyService;

    @PostMapping
    @ApiOperation(value = "发起好友申请", notes = "发起好友申请")
    public UnifiedResult apply(@RequestBody UserFriendApplyRequest request) throws Exception {

        Integer userId = RequestUtil.getUserId();
        LOGGER.info("{} invoke friend apply. request = {}, operator = {}", LOGGER_PREFIX, request, userId);
        try {
            userFriendApplyService.apply(request);
            return UnifiedResult.success();
        } catch (CaException e) {
            LOGGER.error("{}  invoke friend apply error. request = {}", LOGGER_PREFIX, request, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
    }

    @PostMapping("/agree")
    @ApiOperation(value = "同意好友申请", notes = "同意好友申请")
    public UnifiedResult agree(@RequestBody UserFriendApplyRequest request) throws Exception {

        Integer userId = RequestUtil.getUserId();
        LOGGER.info("{} agree friend apply. request = {}, operator = {}", LOGGER_PREFIX, request, userId);
        try {
            userFriendApplyService.agree(request);
            return UnifiedResult.success();
        } catch (CaException e) {
            LOGGER.error("{}  agree friend apply error. request = {}", LOGGER_PREFIX, request, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
    }

    @PostMapping("/refuse")
    @ApiOperation(value = "拒绝好友申请", notes = "拒绝好友申请")
    public UnifiedResult refuse(@RequestBody UserFriendApplyRequest request) throws Exception {

        Integer userId = RequestUtil.getUserId();
        LOGGER.info("{} refuse friend apply. request = {}, operator = {}", LOGGER_PREFIX, request, userId);
        try {
            userFriendApplyService.refuse(request);
            return UnifiedResult.success();
        } catch (CaException e) {
            LOGGER.error("{}  refuse friend apply error. request = {}", LOGGER_PREFIX, request, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
    }

    @GetMapping("/my/unprocessed/count")
    @ApiOperation(value = "我未处理的好友申请数量", notes = "我未处理的好友申请数量")
    public UnifiedResult<Integer> countByMyUnprocessed() throws Exception {

        LOGGER.info("{} query my unprocessed friend apply count, operator = {}", LOGGER_PREFIX, RequestUtil.getUserId().toString());
        return UnifiedResult.success(userFriendApplyService.countByMyUnprocessed());
    }


    @PostMapping("/page/my/apply")
    @ApiOperation(value = "分页查询我的申请列表", notes = "分页查询我的申请列表")
    public UnifiedResult<PageData<UserFriendApplyModel>> pageByMyApply(@RequestBody UserFriendApplyQueryRequest request) throws Exception {

        LOGGER.info("{} page my friend apply list by request. request = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        return UnifiedResult.success(userFriendApplyService.pageByMyApply(request));
    }

    @PostMapping("/page/my/handle")
    @ApiOperation(value = "分页查询我的好友申请处理列表", notes = "分页查询我的好友申请处理列表")
    public UnifiedResult<PageData<UserFriendApplyModel>> pageByMyHandle(@RequestBody UserFriendApplyQueryRequest request) throws Exception {

        LOGGER.info("{} page my friend handle list by request. request = {}, operator = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        return UnifiedResult.success(userFriendApplyService.pageByMyHandle(request));
    }
}
