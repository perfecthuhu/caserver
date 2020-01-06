package com.card.alumni.security.utils;

import com.card.alumni.exception.BadRequestException;
import com.card.alumni.security.entity.JwtUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {

    /**
     * 获取用户详情
     *
     * @return 用户详细信息
     */
    public static UserDetails getUserDetails() {
        UserDetails userDetails;
        try {
            userDetails = (UserDetails) org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new BadRequestException(HttpStatus.UNAUTHORIZED, "登录状态过期");
        }
        return userDetails;
    }

    /**
     * 获取系统用户名称
     *
     * @return 系统用户名称
     */
    public static String getUsername() {
        JwtUser user = (JwtUser) getUserDetails();
        return user.getUsername();
    }

    /**
     * 获取系统用户id
     *
     * @return 系统用户id
     */
    public static Integer getUserId() {
        JwtUser user = (JwtUser) getUserDetails();
        return user.getId();
    }
}
