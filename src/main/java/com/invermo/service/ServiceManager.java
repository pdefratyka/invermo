package com.invermo.service;

import com.invermo.persistance.repository.UserRepository;
import com.invermo.persistance.repository.impl.UserRepositoryImpl;
import com.invermo.service.impl.AuthenticationServiceImpl;

public class ServiceManager {
    private static AuthenticationService authenticationService;
    private static UserRepository userRepository;

    public static AuthenticationService getAuthenticationService() {
        if (authenticationService == null) {
            authenticationService = new AuthenticationServiceImpl(getUserRepository());
        }
        return authenticationService;
    }

    private static UserRepository getUserRepository() {
        if (userRepository == null) {
            userRepository = new UserRepositoryImpl();
        }
        return userRepository;
    }
}
