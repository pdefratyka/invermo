package com.invermo.application.service.impl;

import com.invermo.application.facade.InnerApplicationFacade;
import com.invermo.application.state.ApplicationState;
import com.invermo.business.domain.User;

import java.util.Optional;

public class AuthenticationService {

    private final InnerApplicationFacade innerApplicationFacade;

    public AuthenticationService(InnerApplicationFacade innerApplicationFacade) {
        this.innerApplicationFacade = innerApplicationFacade;
    }

    public boolean login(final String login, final String password) {
        final Optional<User> user = innerApplicationFacade.login(login, password);
        if (user.isPresent()) {
            ApplicationState.setUser(user.get());
            return true;
        }
        return false;
    }
}
