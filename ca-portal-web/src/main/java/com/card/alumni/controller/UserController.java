package com.card.alumni.controller;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.service.UserService;
import com.card.alumni.utils.AESUtil;
import com.card.alumni.utils.CookieUtils;
import com.card.alumni.utils.RedisUtils;
import com.card.alumni.utils.RequestUtil;
import com.card.alumni.vo.CaProtalWebConstants;
import com.card.alumni.vo.UserVO;
import com.card.alumni.vo.query.UserQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @param phone
     * @param code
     * @return
     * @throws Exception
     */
    @PostMapping("/login")
    @ApiOperation(value = "登陆", notes = "登陆", response = UnifiedResponse.class)
    public UnifiedResponse login(String phone, String code, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserVO userVO = new UserVO();
        userVO.setPhone(phone);
        userService.login(userVO, code);
        Integer id = RequestUtil.getUserId();
        String token = AESUtil.encrypt(id.toString(), "ca_manager_aes_token_pwd");
        CookieUtils.setCookie(request, response, "token", token, 60 * 30, "utf-8");
        response.setHeader("token", token);
        redisUtils.set(token, "", CaProtalWebConstants.TOKEN_EXPIRE_TIME);
        return new UnifiedResponse();
    }

    /**
     * 填写用户信息
     * @param userVO
     * @return
     */
    @PostMapping("/submit")
    @ApiOperation(value = "填写用户信息", notes = "填写用户信息", response = UnifiedResponse.class)
    public UnifiedResponse submitUserInfo(@RequestBody UserVO userVO) throws Exception {
        userService.submitUserInfo(userVO);
        return new UnifiedResponse();
    }

    /**
     * 查询用户信息
     * @param userQuery
     * @return
     */
    @PostMapping("/query")
    @ApiOperation(value = "查询用户信息", notes = "查询用户信息", response = UnifiedResponse.class)
    public UnifiedResponse queryUserVO(@RequestBody UserQuery userQuery) {
        return new UnifiedResponse(userService.queryUserVO(userQuery));
    }

    /**
     * 注册用户
     * @param phone
     * @param code
     * @return
     */
    @PostMapping("/register")
    @ApiOperation(value = "注册用户", notes = "注册用户", response = UnifiedResponse.class)
    public UnifiedResponse register(String phone, String code) throws Exception {
        UserVO userVO = new UserVO();
        userVO.setPhone(phone);
        userService.register(userVO, code);
        return new UnifiedResponse();
    }

    /**
     * 发送验证码
     * @param phone
     * @return
     * @throws Exception
     */
    @PostMapping("/send/code")
    @ApiOperation(value = "发送验证码", notes = "发送验证码", response = UnifiedResponse.class)
    public UnifiedResponse sendCode(String phone) throws Exception {
        userService.sendValidateCode(phone);
        return new UnifiedResponse();
    }
}
