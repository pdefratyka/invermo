package com.invermo.persistance.repository.impl.users;

import com.invermo.persistance.entity.User;
import com.invermo.persistance.repository.AbstractRepository;
import com.invermo.persistance.repository.UserRepository;
import com.invermo.persistance.tables.UsersTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Logger;


public class UserRepositoryImpl extends AbstractRepository implements UserRepository {

    private static final Logger logger = Logger.getLogger(UserRepositoryImpl.class.getName());

    @Override
    public Optional<User> findUserByLoginAndPassword(final String login, final String password) {
        logger.info("Get user from database by login: " + login + " and password");
        return getUserFromDB(login, password);
    }

    private Optional<User> getUserFromDB(final String login, final String password) {
        final String query = prepareGetUserFromDbQuery(login, password);
        return executeQuery(query, this::extractUserFromResultSet);
    }

    private Optional<User> extractUserFromResultSet(final ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                final long userId = resultSet.getLong(UsersTable.USER_ID_COLUMN);
                final String userName = resultSet.getString(UsersTable.LOGIN_COLUMN);
                final String userEmail = resultSet.getString(UsersTable.USER_EMAIL_COLUMN);
                return Optional.of(new User(userId, userName, userEmail));
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error during processing ResultSet", ex);
        }
        return Optional.empty();
    }

    private String prepareGetUserFromDbQuery(final String login, final String password) {
        return UsersDatabaseQueries.getUserByIdAndPassword(login, password);
    }
}
