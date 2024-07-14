package com.invermo.application.state;

import com.invermo.business.domain.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApplicationState {

    private static User user;

    public static void setUser(User user) {
        ApplicationState.user = user;
    }

    public static User getUser() {
        return user;
    }
}
