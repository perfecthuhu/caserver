package com.card.alumni.context;


/**
 * @author liumingyu
 * @date 2019-08-18 PM 20:44
 */
public class UserContext {

    private static ThreadLocal<User> currentUser = new InheritableThreadLocal<>();

    public static User getCurrentUser() {
        User user = currentUser.get();
        if (user == null) {
            return null;
        }
        return user;
    }

    public static void setCurrentUser(User user) {
        currentUser.set(user);
    }

    public static void removeCurrentUser() {
        currentUser.remove();
    }
}
