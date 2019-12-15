package com.card.alumni.service;

import com.card.alumni.context.User;
import com.card.alumni.context.UserContext;

import java.util.Objects;

/**
 * @author sunxiaodong10 2019/12/12
 * @date 9:51 PM
 */
public class BaseService {

    protected User getUser() {
        return UserContext.getCurrentUser().getUser();
    }

    protected Integer getUserId() {
        User user = getUser();
        if (Objects.isNull(user)) {
            return -1;
        }
        return user.getId();
    }
}
