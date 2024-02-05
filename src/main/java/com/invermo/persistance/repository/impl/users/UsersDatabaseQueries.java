package com.invermo.persistance.repository.impl.users;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UsersDatabaseQueries {

    static String getUserByIdAndPassword(final String login, final String password) {
        return "SELECT * FROM users " +
                "where login='" + login + "' " +
                "and password='" + password + "'";
    }
}
