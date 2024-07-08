package com.invermo.business.service.persistence;

import com.invermo.business.domain.User;
import com.invermo.business.facade.InnerBusinessFacade;

import java.util.Optional;

public class UserPersistenceService {

    private final InnerBusinessFacade innerBusinessFacade;

    public UserPersistenceService(InnerBusinessFacade innerBusinessFacade) {
        this.innerBusinessFacade = innerBusinessFacade;
    }

    public Optional<User> findUserByLoginAndPassword(final String login, final String password) {
        return innerBusinessFacade.findUserByLoginAndPassword(login, password);
    }
}
