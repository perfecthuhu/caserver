package com.card.alumni.controller;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.security.annotation.IgnoreLogin;
import com.card.alumni.security.entity.AuthInfo;
import com.card.alumni.security.entity.AuthUser;
import com.card.alumni.security.entity.JwtUser;
import com.card.alumni.security.service.OnlineUserService;
import com.card.alumni.security.utils.JwtTokenUtil;
import com.card.alumni.security.utils.SecurityUtils;
import com.card.alumni.service.UserService;
import com.card.alumni.utils.EncryptUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 授权、根据token获取用户详细信息
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@Api(tags = "系统：系统授权接口")
public class AuthenticationController {

    @Value("${jwt.codeKey}")
    private String codeKey;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier(value = "jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private OnlineUserService onlineUserService;

    @ApiOperation("登录授权")
    @IgnoreLogin
    @PostMapping(value = "/login")
    public UnifiedResponse login(@Validated @RequestBody AuthUser authUser, HttpServletRequest request) throws Exception {

        final JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(authUser.getUsername());

        if (!jwtUser.getPassword().equals(EncryptUtils.encryptPassword(authUser.getPassword()))) {
            throw new AccountExpiredException("密码错误");
        }

        if (!jwtUser.isEnabled()) {
            throw new AccountExpiredException("账号已停用，请联系管理员");
        }
        // 生成令牌
        final String token = jwtTokenUtil.generateToken(jwtUser);
        // 保存在线信息
        onlineUserService.save(jwtUser, token, request);
        // 返回 token
        return new UnifiedResponse(new AuthInfo(token, jwtUser));
    }

    @ApiOperation("获取用户信息")
    @GetMapping(value = "/info")
    public UnifiedResponse getUserInfo() throws Exception {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        return new UnifiedResponse(jwtUser);
    }

    @ApiOperation("更改用户密码")
    @PutMapping(value = "/pwd")
    public UnifiedResponse modifyUserPwd(@Validated @RequestBody AuthUser authUser, HttpServletRequest request) throws Exception {
        final JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(authUser.getUsername());

        if (StringUtils.isBlank(authUser.getOldPassword()) &&
                !jwtUser.getPassword().equals(EncryptUtils.encryptPassword(authUser.getOldPassword()))) {
            throw new AccountExpiredException("旧密码验证错误");
        }

        if (!jwtUser.isEnabled()) {
            throw new AccountExpiredException("账号已停用，请联系管理员");
        }

        userService.updatePwd(jwtUser.getId(), authUser.getPassword());

        onlineUserService.logout(jwtTokenUtil.getToken(request));

        return new UnifiedResponse();
    }

    @ApiOperation("退出登录")
    @IgnoreLogin
    @DeleteMapping(value = "/logout")
    public UnifiedResponse logout(HttpServletRequest request) throws Exception {
        onlineUserService.logout(jwtTokenUtil.getToken(request));
        return new UnifiedResponse();
    }
}
