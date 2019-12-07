package com.card.alumni.context;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author liumingyu
 * @date 2019-08-18 PM 20:44
 */
@Getter
@Setter
@NoArgsConstructor
public class UserContext {

    private static ThreadLocal<UserContext> currentUser = new InheritableThreadLocal<>();

    private User user;

    private SystemLoginStatusEnum loginStatus;

    public static UserContext getCurrentUser() {
        UserContext user = currentUser.get();
        if (user == null) {
            return null;
        }
        return user;
    }

    public static void setCurrentUser(UserContext user) {
        currentUser.set(user);
    }

    public static void removeCurrentUser() {
        currentUser.remove();
    }
}
