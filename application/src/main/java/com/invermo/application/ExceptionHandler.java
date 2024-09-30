package com.invermo.application;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("An exception occurred", e);
        throw new RuntimeException(e);
    }
}
