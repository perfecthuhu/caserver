package com.card.alumni.controller;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.exception.CaException;
import com.card.alumni.request.UserFriendQueryRequest;
import com.card.alumni.service.UserFriendService;
import com.card.alumni.utils.RequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liumingyu
 * @date 2019-10-04 2:26 PM
 */
@RestController
@Api(value = "用户好友模块", tags = "用户好友模块")
@RequestMapping("/api/user/friend")
public class UserFriendController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserFriendController.class);

    private static final String LOGGER_PREFIX = "【用户好友模块】";

    @Autowired
    private UserFriendService userFriendService;

    @DeleteMapping("/{friendId}")
    @ApiOperation(value = "删除我的好友", notes = "删除我的好友", response = UnifiedResponse.class)
    public UnifiedResponse deleteMyFriend(@PathVariable("friendId") Integer friendId) throws Exception {

        Integer userId = RequestUtil.getUserId();
        LOGGER.info("{} delete my friend by friendId. friendId = {}, operator = {}", LOGGER_PREFIX, friendId, userId);
        try {
            userFriendService.deleteByUserIdAndFriendId(userId, friendId);
            return new UnifiedResponse();
        } catch (CaException e) {
            LOGGER.error("{}  delete my friend by friendId error. friendId = {}", LOGGER_PREFIX, friendId, e);
            return new UnifiedResponse(e.getCode(), e.getMessage());
        }
    }

    @PostMapping("/page")
    @ApiOperation(value = "分页查询我的好友列表", notes = "分页查询我的好友列表", response = UnifiedResponse.class)
    public UnifiedResponse pageByRequest(@RequestBody UserFriendQueryRequest request) throws Exception {

        LOGGER.info("{} page my friends by request. request = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        return new UnifiedResponse(userFriendService.pageByRequest(request));
    }

}
