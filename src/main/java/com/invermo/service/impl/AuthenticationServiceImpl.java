package com.invermo.service.impl;

import com.invermo.persistance.entity.User;
import com.invermo.persistance.repository.UserRepository;
import com.invermo.service.AuthenticationService;
import com.invermo.state.ApplicationState;
import com.invermo.utils.HashingService;

import java.util.Optional;

public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean login(final String login, final String password) {
        final String hashedPassword = HashingService.hashText(password);
        final Optional<User> user = userRepository.findUserByLoginAndPassword(login, hashedPassword);
        if (user.isPresent()) {
            ApplicationState.setUser(user.get());
            return true;
        }
        return false;
    }
}
