package com.invermo.application.persistance.repository;

import com.invermo.application.utils.DbHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

public abstract class AbstractRepository {

    private static final int MAX_BATCH_SIZE = 1000;

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

    protected void executeQueriesInBatch(final List<String> queries) {
        logger.info("Execute query: " + queries);
        final List<List<String>> chunksOfQueries = divideQueriesIntoChunks(queries);
        logger.info("Number of chunks: " + chunksOfQueries.size());
        try (Connection connection = DbHelper.getDbConnection();
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            for (List<String> tempQueries : chunksOfQueries) {
                for (String query : tempQueries) {
                    statement.addBatch(query);
                }
                statement.executeBatch();
                connection.commit();
            }
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

    private List<List<String>> divideQueriesIntoChunks(final List<String> queries) {
        List<List<String>> chunks = new ArrayList<>();
        for (int i = 0; i < queries.size(); i += MAX_BATCH_SIZE) {
            int end = Math.min(queries.size(), i + MAX_BATCH_SIZE);
            chunks.add(queries.subList(i, end));
        }
        return chunks;
    }
}
