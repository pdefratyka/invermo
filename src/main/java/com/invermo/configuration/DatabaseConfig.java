package com.invermo.configuration;

public final class DatabaseConfig {

    public static final String DB_URL = "jdbc:postgresql://localhost:5432/invermo_dev?currentSchema=invermo";
    public static String LOGIN = System.getenv("db_login");
    public static String PASSWORD = System.getenv("db_password");

    private DatabaseConfig() {
    }
}