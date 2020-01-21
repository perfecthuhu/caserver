package com.card.alumni.controller;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResult;
import com.card.alumni.entity.CaUser;
import com.card.alumni.exception.CaException;
import com.card.alumni.model.SimpleUserModel;
import com.card.alumni.request.FeedbackRequest;
import com.card.alumni.service.UserService;
import com.card.alumni.utils.AESUtil;
import com.card.alumni.utils.CookieUtils;
import com.card.alumni.utils.RedisUtils;
import com.card.alumni.utils.RequestUtil;
import com.card.alumni.vo.CaPortalWebConstants;
import com.card.alumni.vo.UserLoginVO;
import com.card.alumni.vo.UserVO;
import com.card.alumni.vo.query.UserPhoneCodeVO;
import com.card.alumni.vo.query.UserQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sunxiaodong10 2019/12/8
 * @date 3:12 PM
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户模块", tags = "用户模块")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RedisUtils redisUtils;

    /**
     * 登陆
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/login")
    @ApiOperation(value = "登陆", notes = "登陆")
    public UnifiedResult<UserLoginVO> login(@RequestBody UserPhoneCodeVO phoneCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserVO userVO = new UserVO();
        userVO.setPhone(phoneCode.getPhone());
        CaUser caUser = userService.login(userVO, phoneCode.getCode());
        Integer id = caUser.getId();
        String token = AESUtil.encrypt(id.toString(), "ca_manager_aes_token_pwd");
        CookieUtils.setCookie(request, response, "token", token, 60 * 30, "utf-8");
        response.setHeader("token", token);
        redisUtils.set("user_login_" + id, token, CaPortalWebConstants.TOKEN_EXPIRE_TIME);
        return UnifiedResult.success(new UserLoginVO(caUser.getId(), caUser.getYn(), token));
    }

    /**
     * 填写用户信息
     *
     * @param userVO
     * @return
     */
    @PostMapping("/submit")
    @ApiOperation(value = "填写用户信息", notes = "填写用户信息")
    public UnifiedResult submitUserInfo(@RequestBody UserVO userVO) throws Exception {
        userService.submitUserInfo(userVO);
        return UnifiedResult.success();
    }

    /**
     * 查询用户信息
     *
     * @param userQuery
     * @return
     */
    @PostMapping("/query")
    @ApiOperation(value = "查询用户信息", notes = "查询用户信息")
    public UnifiedResult<PageData<UserVO>> queryUserVO(@RequestBody UserQuery userQuery) {
        return UnifiedResult.success(userService.queryUserVO(userQuery));
    }

    /**
     * 发送验证码
     *
     * @param phone
     * @return
     * @throws Exception
     */
    @PostMapping("/send/code")
    @ApiOperation(value = "发送验证码", notes = "发送验证码")
    public UnifiedResult sendCode(@RequestBody UserPhoneCodeVO phone) throws Exception {
        userService.sendValidateCode(phone.getPhone());
        return UnifiedResult.success();
    }

    @GetMapping("/my")
    @ApiOperation(value = "我的信息", notes = "我的信息")
    public UnifiedResult<UserVO> findMyUserInfo() throws Exception {
        return UnifiedResult.success(userService.findMyUserInfo());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "查询用户信息", notes = "查询用户信息")
    public UnifiedResult<SimpleUserModel> findUserById(@PathVariable("id") Integer id) throws Exception {
        return UnifiedResult.success(userService.findUserById(id));
    }

    @GetMapping("/loginOut")
    @ApiOperation(value = "登出", notes = "登出")
    public UnifiedResult<Boolean> loginOut() throws CaException {
        userService.loginOut(RequestUtil.getUserId());
        return UnifiedResult.success(true);
    }

    @PostMapping("/feedback")
    @ApiOperation(value = "提交用户反馈", notes = "提交用户反馈")
    public UnifiedResult<Boolean> saveFeedBack(@RequestBody FeedbackRequest request) throws CaException {
        userService.saveFeedBack(request);
        return UnifiedResult.success(true);
    }
}
