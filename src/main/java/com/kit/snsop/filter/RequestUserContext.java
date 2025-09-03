package com.kit.snsop.filter;

public class RequestUserContext {

    private static final ThreadLocal<Long> currentUser = new ThreadLocal<>();

    public static void setUser(Long userId) {
        currentUser.set(userId);
    }

    public static Long getUser() {
        return currentUser.get();
    }

    public static void clear() {
        currentUser.remove();
    }
}

