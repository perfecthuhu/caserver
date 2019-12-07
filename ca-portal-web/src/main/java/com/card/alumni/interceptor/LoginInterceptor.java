package com.card.alumni.interceptor;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.context.User;
import com.card.alumni.context.UserContext;
import com.card.alumni.service.UserService;
import com.card.alumni.utils.AESUtil;
import com.card.alumni.utils.CookieUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


/**
 * @author sunxiaodong10 2019/12/7
 * @date 1:29 PM
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserContext userContext = UserContext.getCurrentUser();

        if(Objects.isNull(userContext)) {
            userContext = new UserContext();
            UserContext.setCurrentUser(userContext);
        }
        User user = getUser(request);
        return super.preHandle(request, response, handler);
    }

    private User getUser(HttpServletRequest request) {
        String token = CookieUtils.getCookieValue(request, "token", "utf-8");
        String tokenId = AESUtil.decrypt(token, "ca_manager_aes_token_pwd");
        UnifiedResponse response = userService.queryUserById(Integer.parseInt(tokenId));
        return null;

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.removeCurrentUser();
        super.afterCompletion(request, response, handler, ex);
    }
}
