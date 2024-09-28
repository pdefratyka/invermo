package com.invermo.persistence.utils;

import com.invermo.persistence.configuration.DatabaseConnectionPool;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DbHelper {

    public static Connection getDbConnection() throws SQLException {
        return DatabaseConnectionPool.getConnection();
    }
}