package com.invermo.application.configuration;

public final class DatabaseConfig {

    public static final String DB_URL = System.getenv("db_url");
    public static final String LOGIN = System.getenv("db_login");
    public static final String PASSWORD = System.getenv("db_password");

    private DatabaseConfig() {
    }
}