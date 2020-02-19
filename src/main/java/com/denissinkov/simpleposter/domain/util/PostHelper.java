package com.denissinkov.simpleposter.domain.util;

import com.denissinkov.simpleposter.domain.User;

public abstract class PostHelper {
    public static String getAuthorName(User user) {
        return user != null ? user.getUsername() : "<none>";
    }
}
