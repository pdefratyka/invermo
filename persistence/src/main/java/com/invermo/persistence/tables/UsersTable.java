package com.invermo.persistence.tables;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UsersTable {

    public static final String USER_ID_COLUMN = "user_id";
    public static final String LOGIN_COLUMN = "login";
    public static final String PASSWORD_COLUMN = "password";
    public static final String USER_EMAIL_COLUMN = "user_email";
}
