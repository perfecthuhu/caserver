package com.card.alumni.interceptor;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.context.SystemLoginStatusEnum;
import com.card.alumni.context.User;
import com.card.alumni.context.UserContext;
import com.card.alumni.service.UserService;
import com.card.alumni.utils.AESUtil;
import com.card.alumni.utils.CookieUtils;
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

    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserContext userContext = UserContext.getCurrentUser();
        String path = request.getRequestURI();
        if(Objects.isNull(userContext)) {
            userContext = new UserContext();
            UserContext.setCurrentUser(userContext);
        }
        if (needLogin(path)) {
            User user = getUser(request);
            if (Objects.isNull(user)) {
                redirect2LoginPage(request, response);
                return false;
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
        //TODO 判断url是否需要登陆
        return true;
    }

    private User getUser(HttpServletRequest request) {
        String token = CookieUtils.getCookieValue(request, "token", "utf-8");
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
