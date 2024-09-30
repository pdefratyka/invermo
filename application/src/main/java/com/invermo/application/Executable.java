package com.invermo.application;

import java.io.IOException;
import java.util.logging.LogManager;

public class Executable {
    public static void main(String... args) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
        initLogging();
        Main.main(args);
    }

    private static void initLogging() {
        try {
            LogManager.getLogManager().readConfiguration(
                    Executable.class.getResourceAsStream("/logging.properties")
            );
        } catch (IOException ex) {
            System.err.println("Error loading logging configuration: " + ex);
        }
    }
}