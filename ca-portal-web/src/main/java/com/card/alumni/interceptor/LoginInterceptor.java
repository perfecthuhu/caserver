package com.card.alumni.interceptor;

import com.card.alumni.context.SystemLoginStatusEnum;
import com.card.alumni.context.User;
import com.card.alumni.context.UserContext;
import com.card.alumni.service.UserService;
import com.card.alumni.utils.AESUtil;
import com.card.alumni.utils.CookieUtils;
import com.card.alumni.utils.RedisUtils;
import com.card.alumni.vo.CaPortalWebConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


/**
 * @author sunxiaodong10 2019/12/7
 * @date 1:29 PM
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);

    @Resource
    private UserService userService;

    @Resource
    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return true;
        }
        response.setCharacterEncoding("utf-8");
        UserContext userContext = UserContext.getCurrentUser();
        String path = request.getRequestURI();
        LOGGER.info(path);
        if (Objects.isNull(userContext)) {
            userContext = new UserContext();
            UserContext.setCurrentUser(userContext);
        }
        if (needLogin(path)) {
            User user = getUser(request);
            if (Objects.isNull(user)) {
                redirect2LoginPage(request, response);
                return false;
            }
            if (user.getYn() == 0) {
                if (!path.startsWith("/user/submit")) {
                    userContext.setLoginStatus(SystemLoginStatusEnum.NOT_PASS);
                    redirect2AddPersonalInformation(request, response);
                    return false;
                }
            }
            if (user.getYn() == 1) {
                userContext.setLoginStatus(SystemLoginStatusEnum.PASS);
            }
            userContext.setUser(user);
        }
        return true;
    }

    private void redirect2AddPersonalInformation(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (isAjaxRequest(request)) {
                response.sendError(HttpStatus.FORBIDDEN.value());
            }
        } catch (IOException e) {
            LOGGER.error("已登陆，未认证重定向失败", e);
        }
    }

    private void redirect2LoginPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (isAjaxRequest(request)) {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
            }
        } catch (IOException e) {
            LOGGER.error("跳转登陆页失败", e);
        }
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        boolean isAjaxReuest = false;
        if (request.getHeader("x-requested-with") != null
                && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
            isAjaxReuest = true;
        }
        return isAjaxReuest;
    }

    private boolean needLogin(String path) {
        if (path.startsWith("/doc.html")) {
            return false;
        }
        if (path.startsWith("/swagger")) {
            return false;
        }
        if (path.startsWith("/user/login")) {
            return false;
        }
        if (path.startsWith("/user/register")) {
            return false;
        }
        if (path.startsWith("/user/send/code")) {
            return false;
        }
        return true;
    }

    private User getUser(HttpServletRequest request) {
        String token = CookieUtils.getCookieValue(request, "token", "utf-8");
        if (StringUtils.isBlank(token)) {
            token = request.getHeader("token");
        }
        if (StringUtils.isBlank(token)) {
            return null;
        }
        String tokenId = AESUtil.decrypt(token, "ca_manager_aes_token_pwd");
        boolean isExist = redisUtils.hasKey("user_login_" + tokenId);
        if (!isExist) {
            return null;
        }
        redisUtils.expire("user_login_" + tokenId, CaPortalWebConstants.TOKEN_EXPIRE_TIME);
        User user = userService.queryUserById(Integer.parseInt(tokenId));
        if (Objects.isNull(user)) {
            return null;
        }
        return user;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.removeCurrentUser();
        super.afterCompletion(request, response, handler, ex);
    }
}
