package com.invermo.business.service;

import com.invermo.business.domain.User;
import com.invermo.business.service.persistence.UserPersistenceService;
import com.invermo.business.utils.HashingService;

import java.util.Optional;

public class AuthenticationService {

    private final UserPersistenceService userPersistenceService;

    public AuthenticationService(UserPersistenceService userPersistenceService) {
        this.userPersistenceService = userPersistenceService;
    }

    public Optional<User> login(final String login, final String password) {
        final String hashedPassword = HashingService.hashText(password);
        return userPersistenceService.findUserByLoginAndPassword(login, hashedPassword);
    }
}
