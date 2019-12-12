package com.card.alumni.controller;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.context.UserContext;
import com.card.alumni.service.UserService;
import com.card.alumni.utils.AESUtil;
import com.card.alumni.utils.CookieUtils;
import com.card.alumni.vo.UserVO;
import com.card.alumni.vo.query.UserQuery;
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
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 登陆
     * @param userVO
     * @param verificatioCode
     * @return
     * @throws Exception
     */
    @RequestMapping("/login")
    public UnifiedResponse login(UserVO userVO, String verificatioCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
        userService.login(userVO, verificatioCode);
        Integer id = UserContext.getCurrentUser().getUser().getId();
        String token = AESUtil.encrypt(id.toString(), "ca_manager_aes_token_pwd");
        CookieUtils.setCookie(request, response, "token", token, 60 * 30, "utf-8");
        return new UnifiedResponse();
    }

    /**
     * 填写用户信息
     * @param userVO
     * @return
     */
    @RequestMapping("/submit")
    public UnifiedResponse submitUserInfo(UserVO userVO) throws Exception {
        userService.submitUserInfo(userVO);
        return new UnifiedResponse();
    }

    /**
     * 查询用户信息
     * @param userQuery
     * @return
     */
    @RequestMapping("/query")
    public UnifiedResponse queryUserVO(UserQuery userQuery) {
        return new UnifiedResponse(userService.queryUserVO(userQuery));
    }

    /**
     * 注册用户
     * @param verificatioCode
     * @return
     */
    @RequestMapping("/register")
    public UnifiedResponse register(UserVO userVO, String verificatioCode) throws Exception {
        userService.register(userVO, verificatioCode);
        return new UnifiedResponse();
    }
}
