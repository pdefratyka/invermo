package com.invermo.persistance.repository;

import com.invermo.persistance.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findUserByLoginAndPassword(String login, String password);
}
