package com.invermo.application.service.impl;

import com.invermo.application.facade.InnerApplicationFacade;
import com.invermo.application.service.AuthenticationService;
import com.invermo.application.state.ApplicationState;
import com.invermo.business.domain.User;

import java.util.Optional;

public class AuthenticationServiceImpl implements AuthenticationService {

    private final InnerApplicationFacade innerApplicationFacade;

    public AuthenticationServiceImpl(InnerApplicationFacade innerApplicationFacade) {
        this.innerApplicationFacade = innerApplicationFacade;
    }

    @Override
    public boolean login(final String login, final String password) {
        final Optional<User> user = innerApplicationFacade.login(login, password);
        if (user.isPresent()) {
            ApplicationState.setUser(user.get());
            return true;
        }
        return false;
    }
}
