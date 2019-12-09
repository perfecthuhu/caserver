package com.card.alumni.controller;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.service.UserService;
import com.card.alumni.vo.UserVO;
import com.card.alumni.vo.query.UserQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    public UnifiedResponse login(UserVO userVO, String verificatioCode) throws Exception {
        return userService.login(userVO, verificatioCode);
    }

    /**
     * 填写用户信息
     * @param userVO
     * @return
     */
    @RequestMapping("/submit")
    public UnifiedResponse submitUserInfo(UserVO userVO) throws Exception {
        return userService.submitUserInfo(userVO);
    }

    /**
     * 查询用户信息
     * @param userQuery
     * @return
     */
    @RequestMapping("/query")
    public UnifiedResponse queryUserVO(UserQuery userQuery) {
        return userService.queryUserVO(userQuery);
    }

    /**
     * 注册用户
     * @param verificatioCode
     * @return
     */
    @RequestMapping("/register")
    public UnifiedResponse register(UserVO userVO, String verificatioCode) throws Exception {
        return userService.register(userVO, verificatioCode);
    }
}
