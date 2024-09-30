package com.invermo.persistence.repository.users;

import com.invermo.persistence.entity.UserEntity;
import com.invermo.persistence.repository.AbstractRepository;
import com.invermo.persistence.tables.UsersTable;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
public class UserRepository extends AbstractRepository {

    public Optional<UserEntity> findUserByLoginAndPassword(final String login, final String password) {
        log.info("Get user from database by login: " + login + " and password");
        return getUserFromDB(login, password);
    }

    private Optional<UserEntity> getUserFromDB(final String login, final String password) {
        final String query = prepareGetUserFromDbQuery(login, password);
        return executeQuery(query, this::extractUserFromResultSet);
    }

    private Optional<UserEntity> extractUserFromResultSet(final ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                final long userId = resultSet.getLong(UsersTable.USER_ID_COLUMN);
                final String userName = resultSet.getString(UsersTable.LOGIN_COLUMN);
                final String userEmail = resultSet.getString(UsersTable.USER_EMAIL_COLUMN);
                return Optional.of(new UserEntity(userId, userName, userEmail));
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
