package com.invermo.persistance.repository;

import com.invermo.utils.DbHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Function;
import java.util.logging.Logger;

public abstract class AbstractRepository {

    private static final Logger logger = Logger.getLogger(AbstractRepository.class.getName());

    protected void execute(final String query) {
        logger.info("Execute query: " + query);
        try (Connection connection = DbHelper.getDbConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException ex) {
            throw new RuntimeException("Error during executing query", ex);
        }
    }

    protected <T> T executeQuery(final String query, Function<ResultSet, T> processingFunction) {
        logger.info("Execute query: " + query);
        try (final Connection connection = DbHelper.getDbConnection();
             final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(query)) {
            return processingFunction.apply(resultSet);
        } catch (SQLException ex) {
            throw new RuntimeException("Error during executing query", ex);
        }
    }
}
