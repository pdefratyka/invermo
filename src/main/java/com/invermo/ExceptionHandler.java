package com.invermo;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final Logger logger = Logger.getLogger(ExceptionHandler.class.getName());

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        logger.log(Level.SEVERE, "An exception occurred", e);
        throw new RuntimeException(e);
    }
}
