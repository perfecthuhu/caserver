package com.card.alumni.controller;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.exception.CaException;
import com.card.alumni.request.UserFriendApplyQueryRequest;
import com.card.alumni.request.UserFriendApplyRequest;
import com.card.alumni.service.UserFriendApplyService;
import com.card.alumni.utils.RequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    @ApiOperation(value = "发起好友申请", notes = "发起好友申请", response = UnifiedResponse.class)
    public UnifiedResponse apply(@RequestBody UserFriendApplyRequest request) throws Exception {

        Integer userId = RequestUtil.getUserId();
        LOGGER.info("{} invoke friend apply. request = {}, operator = {}", LOGGER_PREFIX, request, userId);
        try {
            userFriendApplyService.apply(request);
            return new UnifiedResponse();
        } catch (CaException e) {
            LOGGER.error("{}  invoke friend apply error. request = {}", LOGGER_PREFIX, request, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
    }

    @PutMapping("/agree")
    @ApiOperation(value = "同意好友申请", notes = "同意好友申请", response = UnifiedResponse.class)
    public UnifiedResponse agree(@RequestBody UserFriendApplyRequest request) throws Exception {

        Integer userId = RequestUtil.getUserId();
        LOGGER.info("{} agree friend apply. request = {}, operator = {}", LOGGER_PREFIX, request, userId);
        try {
            userFriendApplyService.agree(request);
            return new UnifiedResponse();
        } catch (CaException e) {
            LOGGER.error("{}  agree friend apply error. request = {}", LOGGER_PREFIX, request, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
    }

    @PutMapping("/refuse")
    @ApiOperation(value = "拒绝好友申请", notes = "拒绝好友申请", response = UnifiedResponse.class)
    public UnifiedResponse refuse(@RequestBody UserFriendApplyRequest request) throws Exception {

        Integer userId = RequestUtil.getUserId();
        LOGGER.info("{} refuse friend apply. request = {}, operator = {}", LOGGER_PREFIX, request, userId);
        try {
            userFriendApplyService.refuse(request);
            return new UnifiedResponse();
        } catch (CaException e) {
            LOGGER.error("{}  refuse friend apply error. request = {}", LOGGER_PREFIX, request, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
    }

    @PostMapping("/my/unprocessed/count")
    @ApiOperation(value = "我未处理的好友申请数量", notes = "我未处理的好友申请数量", response = UnifiedResponse.class)
    public UnifiedResponse countByMyUnprocessed() throws Exception {

        LOGGER.info("{} query my unprocessed friend apply count, operator = {}", LOGGER_PREFIX, RequestUtil.getUserId().toString());
        return new UnifiedResponse(userFriendApplyService.countByMyUnprocessed());
    }


    @PostMapping("/page/my/apply")
    @ApiOperation(value = "分页查询我的申请列表", notes = "分页查询我的申请列表", response = UnifiedResponse.class)
    public UnifiedResponse pageByMyApply(@RequestBody UserFriendApplyQueryRequest request) throws Exception {

        LOGGER.info("{} page my friend apply list by request. request = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        return new UnifiedResponse(userFriendApplyService.pageByMyApply(request));
    }

    @PostMapping("/page/my/handle")
    @ApiOperation(value = "分页查询我的好友申请处理列表", notes = "分页查询我的好友申请处理列表", response = UnifiedResponse.class)
    public UnifiedResponse pageByMyHandle(@RequestBody UserFriendApplyQueryRequest request) throws Exception {

        LOGGER.info("{} page my friend handle list by request. request = {}, operator = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        return new UnifiedResponse(userFriendApplyService.pageByMyHandle(request));
    }
}
