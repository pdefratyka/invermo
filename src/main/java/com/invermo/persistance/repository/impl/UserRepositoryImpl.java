package com.invermo.persistance.repository.impl;

import com.invermo.persistance.entity.User;
import com.invermo.persistance.entity.UsersTableConfig;
import com.invermo.persistance.repository.UserRepository;
import com.invermo.utils.DbHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.logging.Logger;


public class UserRepositoryImpl implements UserRepository {

    private static final Logger logger = Logger.getLogger(UserRepositoryImpl.class.getName());

    @Override
    public Optional<User> findUserByLoginAndPassword(final String login, final String password) {
        logger.info("Get user from database by login: " + login + " and password");
        return getUserFromDB(login, password);
    }

    private Optional<User> getUserFromDB(final String login, final String password) {
        final String query = prepareGetUserFromDbQuery(login, password);
        logger.info("Execute query: " + query);
        try (Connection connection = DbHelper.getDbConnection()) {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery(query);
            return extractUserFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private String prepareGetUserFromDbQuery(final String login, final String password) {
        return "SELECT * FROM users " +
                "where login='" + login + "' " +
                "and password='" + password + "'";
    }

    private Optional<User> extractUserFromResultSet(final ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            final long userId = resultSet.getLong(UsersTableConfig.USER_ID_COLUMN);
            final String userName = resultSet.getString(UsersTableConfig.LOGIN_COLUMN);
            final String userEmail = resultSet.getString(UsersTableConfig.USER_EMAIL_COLUMN);
            return Optional.of(new User(userId, userName, userEmail));
        }
        return Optional.empty();
    }
}
