package com.invermo.configuration;

public final class DatabaseConfig {

    public static final String DB_URL = System.getenv("db_url");
    public static String LOGIN = System.getenv("db_login");
    public static String PASSWORD = System.getenv("db_password");

    private DatabaseConfig() {
    }
}