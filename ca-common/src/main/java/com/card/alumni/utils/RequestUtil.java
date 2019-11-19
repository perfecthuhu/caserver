package com.card.alumni.utils;

import com.card.alumni.context.User;
import com.card.alumni.context.UserContext;

import java.util.Objects;

/**
 * @author liumingyu
 * @date 2019-11-19 10:43 PM
 */
public class RequestUtil {

    public static User getUser() {
        return UserContext.getCurrentUser();
    }

    public static Long getUserId() {
        User user = UserContext.getCurrentUser();
        if (Objects.isNull(user)) {
            return -1L;
        }
        return user.getId();
    }

    public static String getUserName() {
        User user = UserContext.getCurrentUser();
        if (Objects.isNull(user)) {
            return "系统";
        }
        return user.getName();
    }
}
