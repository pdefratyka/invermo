package com.invermo.state;

import com.invermo.persistance.entity.User;

public final class ApplicationState {

    private static User user;

    private ApplicationState() {
    }

    public static void setUser(User user) {
        ApplicationState.user = user;
    }

    public static User getUser() {
        return user;
    }
}
