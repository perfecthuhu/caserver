package com.card.alumni.utils;

import com.card.alumni.context.User;
import com.card.alumni.context.UserContext;

import java.util.Objects;

/**
 * @author liumingyu
 * @date 2019-11-19 10:43 PM
 */
public class RequestUtil {

    /**
     * 获取当前用户
     *
     * @return 用户信息
     */
    public static User getUser() {
        UserContext context = UserContext.getCurrentUser();

        return Objects.isNull(context) ? null : context.getUser();
    }

    /**
     * 获取当前用户
     *
     * @return 用户ID
     */
    public static Integer getUserId() {
        User user = getUser();
        if (Objects.isNull(user)) {
            return -1;
        }
        return user.getId();
    }

    /**
     * 获取当前用户名称
     *
     * @return 用户名称
     */
    public static String getUserName() {
        User user = getUser();
        if (Objects.isNull(user)) {
            return "系统";
        }
        return user.getName();
    }

    public static String getUserAvatar() {
        User user = getUser();
        if (Objects.isNull(user)) {
            return "";
        }
        return user.getPhotoImg();
    }
}
