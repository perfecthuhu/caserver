package com.card.alumni.controller;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResult;
import com.card.alumni.entity.CaUserFriend;
import com.card.alumni.exception.CaException;
import com.card.alumni.model.SimpleUserModel;
import com.card.alumni.request.UserFriendQueryRequest;
import com.card.alumni.service.UserFriendService;
import com.card.alumni.utils.RequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

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
    @ApiOperation(value = "删除我的好友", notes = "删除我的好友")
    public UnifiedResult deleteMyFriend(@PathVariable("friendId") Integer friendId) throws Exception {

        Integer userId = RequestUtil.getUserId();
        LOGGER.info("{} delete my friend by friendId. friendId = {}, operator = {}", LOGGER_PREFIX, friendId, userId);
        try {
            userFriendService.deleteByUserIdAndFriendId(userId, friendId);
            return UnifiedResult.success();
        } catch (CaException e) {
            LOGGER.error("{}  delete my friend by friendId error. friendId = {}", LOGGER_PREFIX, friendId, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
    }

    @PostMapping("/page")
    @ApiOperation(value = "分页查询我的好友列表", notes = "分页查询我的好友列表")
    public UnifiedResult<PageData<SimpleUserModel>> pageFriendsByRequest(@RequestBody UserFriendQueryRequest request) throws Exception {

        LOGGER.info("{} page my friends by request. request = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        return UnifiedResult.success(userFriendService.pageFriendsByRequest(request));
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询我的全部好友列表", notes = "查询我的全部好友列表")
    public UnifiedResult<List<SimpleUserModel>> listMyAllFriends() throws Exception {
        LOGGER.info("{} list my friends by request. operator = {}", LOGGER_PREFIX, RequestUtil.getUserId().toString());
        return UnifiedResult.success(userFriendService.listMyFriends());
    }

    @GetMapping("/count")
    @ApiOperation(value = "我的好友数量", notes = "查询我的全部好友列表")
    public UnifiedResult<Integer> countMyAllFriends() throws Exception {
        LOGGER.info("{} count my friends by request. operator = {}", LOGGER_PREFIX, RequestUtil.getUserId().toString());
        return UnifiedResult.success(userFriendService.countMyFriends());
    }

    @PostMapping("/{friendId}/check")
    @ApiOperation(value = "校验当前操作人与该用户是否是好友", notes = "校验当前操作人与该用户是否是好友")
    public UnifiedResult<Boolean> checkFriendShip(@PathVariable("friendId") Integer friendId) throws Exception {
        Integer currentUserId = RequestUtil.getUserId();
        LOGGER.info("{} check is my friend ?. friendId = {}, operatorId = {}", LOGGER_PREFIX, friendId, currentUserId);
        CaUserFriend friend = userFriendService.findByUserIdAndFriendId(currentUserId, friendId);
        return UnifiedResult.success(Objects.nonNull(friend));
    }
}
