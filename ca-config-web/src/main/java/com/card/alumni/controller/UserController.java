package com.card.alumni.controller;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResult;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.SimpleUserModel;
import com.card.alumni.model.UserFriendApplyModel;
import com.card.alumni.model.UserModel;
import com.card.alumni.request.UserFriendApplyQueryRequest;
import com.card.alumni.request.UserFriendQueryRequest;
import com.card.alumni.request.UserQueryRequest;
import com.card.alumni.request.UserRequest;
import com.card.alumni.service.UserFriendApplyService;
import com.card.alumni.service.UserFriendService;
import com.card.alumni.service.UserService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liumingyu
 * @date 2019-12-09 9:55 PM
 */
@Api(value = "用户模块", tags = "用户模块")
@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private static final String LOGGER_PREFIX = "【用户模块】";

    @Autowired
    private UserService userService;

    @Autowired
    private UserFriendService userFriendService;

    @Autowired
    private UserFriendApplyService userFriendApplyService;

    @PostMapping
    @ApiOperation(value = "保存用户", notes = "保存用户")
    public UnifiedResult<Integer> save(@RequestBody UserRequest request) throws Exception {
        LOGGER.info("{} save user. request = {}, operator = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        try {
            return UnifiedResult.success(userService.save(request));
        } catch (CaConfigException e) {
            LOGGER.error("{} save user error. request = {}", LOGGER_PREFIX, request, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
    }

    @PutMapping
    @ApiOperation(value = "更新用户", notes = "更新用户")
    public UnifiedResult update(@RequestBody UserRequest request) throws Exception {
        LOGGER.info("{} update user. request = {}, operator = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        try {
            userService.update(request);
        } catch (CaConfigException e) {
            LOGGER.error("{} update user error. request = {}", LOGGER_PREFIX, request, e);
            return UnifiedResult.failure(e.getCode(), e.getMessage());
        }
        return UnifiedResult.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据ID删除用户", notes = "根据ID删除用户")
    public UnifiedResult deleteById(@PathVariable("id") Integer id) throws Exception {
        LOGGER.info("{} delete user by id. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());
        userService.deleteById(id);
        return UnifiedResult.success();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询用户", notes = "根据ID查询用户")
    public UnifiedResult<UserModel> findById(@PathVariable("id") Integer id) throws Exception {

        LOGGER.info("{} find user by id. id = {}, operator = {}", LOGGER_PREFIX, id, RequestUtil.getUserId().toString());

        return UnifiedResult.success(userService.findModelById(id));

    }

    @PostMapping("/page")
    @ApiOperation(value = "分页查询用户列表", notes = "分页查询用户列表")
    public UnifiedResult<PageData<UserModel>> pageByRequest(@RequestBody UserQueryRequest request) throws Exception {

        LOGGER.info("{} page users by request. request = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());

        return UnifiedResult.success(userService.pageByRequest(request));
    }

    @DeleteMapping("/{userId}/friend/{friendId}")
    @ApiOperation(value = "删除用户的好友", notes = "删除用户的好友")
    public UnifiedResult deleteMyFriend(@PathVariable("userId") Integer userId, @PathVariable("friendId") Integer friendId) throws Exception {
        LOGGER.info("{} delete user friend relation. userId = {}, friendId = {}", LOGGER_PREFIX, userId, friendId);
        userFriendService.deleteByUserIdAndFriendId(userId, friendId);
        return UnifiedResult.success();
    }

    @PostMapping("/page/friend/list")
    @ApiOperation(value = "分页查询用户的好友列表", notes = "分页查询用户的好友列表")
    public UnifiedResult<PageData<SimpleUserModel>> pageFriendsByRequest(@RequestBody UserFriendQueryRequest request) throws Exception {
        LOGGER.info("{} page friends by request. request = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        return UnifiedResult.success(userFriendService.pageFriendsByRequest(request));
    }

    @PostMapping("/page/friend/apply/list")
    @ApiOperation(value = "分页查询用户的好友申请列表", notes = "分页查询用户的好友申请列表")
    public UnifiedResult<PageData<UserFriendApplyModel>> pageFriendApplyByRequest(@RequestBody UserFriendApplyQueryRequest request) throws Exception {
        LOGGER.info("{} page user friend apply list by request. request = {}", LOGGER_PREFIX, request, RequestUtil.getUserId().toString());
        return UnifiedResult.success(userFriendApplyService.pageByRequest(request));
    }

}
