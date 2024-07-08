package com.invermo.application.utils;

import com.invermo.application.configuration.DatabaseConfig;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DbHelper {

    public static Connection getDbConnection() throws SQLException {
        return DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.LOGIN, DatabaseConfig.PASSWORD);
    }
}