package com.invermo.persistance.repository.impl.portfolio.transaction;

import com.invermo.persistance.entity.Transaction;
import com.invermo.persistance.enumeration.TransactionType;
import com.invermo.persistance.repository.AbstractRepository;
import com.invermo.persistance.repository.TransactionRepository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepositoryImpl extends AbstractRepository implements TransactionRepository {

    @Override
    public void saveTransaction(final Transaction transaction) {
        final String query = prepareSaveTransactionQuery(transaction);
        execute(query);
    }

    @Override
    public List<Transaction> getAllTransactionsForPositions(final List<Long> positionsIds) {
        final String query = prepareGetAllTransactionsForPositionsQuery(positionsIds);
        return executeQuery(query, this::extractTransactionsFromResultSet);
    }

    private List<Transaction> extractTransactionsFromResultSet(final ResultSet resultSet) {
        try {
            final List<Transaction> transactions = new ArrayList<>();
            while (resultSet.next()) {
                final Transaction transaction = buildTransactionFromResultSet(resultSet);
                transactions.add(transaction);
            }
            return transactions;
        } catch (SQLException ex) {
            throw new RuntimeException("Error during processing ResultSet", ex);
        }
    }

    private Transaction buildTransactionFromResultSet(final ResultSet resultSet) throws SQLException {
        final Long transactionId = resultSet.getLong("transaction_id");
        final Long positionId = resultSet.getLong("position_id");
        final LocalDateTime date = resultSet.getObject("date", LocalDateTime.class);
        final BigDecimal numberOfAssets = resultSet.getBigDecimal("number_of_asset");
        final BigDecimal pricePerAsset = resultSet.getBigDecimal("price_per_asset");
        final BigDecimal currencyExchange = resultSet.getBigDecimal("currency_exchange");
        final String transactionType = resultSet.getString("transaction_type");
        return Transaction.builder()
                .transactionId(transactionId)
                .positionId(positionId)
                .dateTime(date)
                .numberOfAsset(numberOfAssets)
                .price(pricePerAsset)
                .currencyExchangeRate(currencyExchange)
                .transactionType(TransactionType.valueOf(transactionType))
                .build();
    }

    private String prepareSaveTransactionQuery(final Transaction transaction) {
        return TransactionsDatabaseQueries.saveTransaction(transaction);
    }

    private String prepareGetAllTransactionsForPositionsQuery(final List<Long> positionsIds) {
        return TransactionsDatabaseQueries.getAllTransactionsForPositions(positionsIds);
    }
}
