package com.card.alumni.interceptor;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.context.SystemLoginStatusEnum;
import com.card.alumni.context.User;
import com.card.alumni.context.UserContext;
import com.card.alumni.service.UserService;
import com.card.alumni.utils.AESUtil;
import com.card.alumni.utils.CookieUtils;
import com.card.alumni.utils.RedisUtils;
import com.card.alumni.vo.CaProtalWebConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;


/**
 * @author sunxiaodong10 2019/12/7
 * @date 1:29 PM
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);

    @Resource
    private UserService userService;

    @Resource
    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserContext userContext = UserContext.getCurrentUser();
        String path = request.getRequestURI();
        LOGGER.info(path);
        if(Objects.isNull(userContext)) {
            userContext = new UserContext();
            UserContext.setCurrentUser(userContext);
        }
        if (needLogin(path)) {
            User user = getUser(request);
            if (Objects.isNull(user)) {
                redirect2LoginPage(request, response);
                return true;
            }
            if (user.getYn() == 1) {
                userContext.setLoginStatus(SystemLoginStatusEnum.PASS);
            } else {
                userContext.setLoginStatus(SystemLoginStatusEnum.NOT_PASS);
            }
            userContext.setUser(user);
        }
        return true;
    }

    private void redirect2LoginPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (isAjaxRequest(request)) {
                PrintWriter writer = response.getWriter();
                writer.write("{\"error\":\"noLogin\"}");
                return;
            }
            response.setHeader("Pragma","No-cache");
            response.setHeader("Cache-Control","no-cache");
            response.setDateHeader("Expires", 0);
            //TODO 登陆URL
            String loginUrl  = "";
            response.sendRedirect(loginUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        boolean isAjaxReuest = false;
        if(request.getHeader("x-requested-with") != null
                && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
            isAjaxReuest =true;
        }
        return  isAjaxReuest;
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
        boolean isExist = redisUtils.hasKey(token);
        if (!isExist) {
            return null;
        }
        redisUtils.expire(token, CaProtalWebConstants.TOKEN_EXPIRE_TIME);
        String tokenId = AESUtil.decrypt(token, "ca_manager_aes_token_pwd");
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
