package com.invermo.persistence.repository.transactions;

import com.invermo.persistence.entity.TransactionEntity;
import com.invermo.persistence.enumeration.TransactionType;
import com.invermo.persistence.repository.AbstractRepository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository extends AbstractRepository {

    public void saveTransaction(final TransactionEntity transaction) {
        final String query = prepareSaveTransactionQuery(transaction);
        execute(query);
    }

    public List<TransactionEntity> getAllTransactionsForPositions(final List<Long> positionsIds) {
        final String query = prepareGetAllTransactionsForPositionsQuery(positionsIds);
        return executeQuery(query, this::extractTransactionsFromResultSet);
    }

    private List<TransactionEntity> extractTransactionsFromResultSet(final ResultSet resultSet) {
        try {
            final List<TransactionEntity> transactions = new ArrayList<>();
            while (resultSet.next()) {
                final TransactionEntity transaction = buildTransactionFromResultSet(resultSet);
                transactions.add(transaction);
            }
            return transactions;
        } catch (SQLException ex) {
            throw new RuntimeException("Error during processing ResultSet", ex);
        }
    }

    private TransactionEntity buildTransactionFromResultSet(final ResultSet resultSet) throws SQLException {
        final Long transactionId = resultSet.getLong("transaction_id");
        final Long positionId = resultSet.getLong("position_id");
        final LocalDateTime date = resultSet.getObject("date", LocalDateTime.class);
        final BigDecimal numberOfAssets = resultSet.getBigDecimal("number_of_asset");
        final BigDecimal pricePerAsset = resultSet.getBigDecimal("price_per_asset");
        final BigDecimal currencyExchange = resultSet.getBigDecimal("currency_exchange");
        final String transactionType = resultSet.getString("transaction_type");
        return TransactionEntity.builder()
                .transactionId(transactionId)
                .positionId(positionId)
                .dateTime(date)
                .numberOfAsset(numberOfAssets)
                .price(pricePerAsset)
                .currencyExchangeRate(currencyExchange)
                .transactionType(TransactionType.valueOf(transactionType))
                .build();
    }

    private String prepareSaveTransactionQuery(final TransactionEntity transaction) {
        return TransactionsDatabaseQueries.saveTransaction(transaction);
    }

    private String prepareGetAllTransactionsForPositionsQuery(final List<Long> positionsIds) {
        return TransactionsDatabaseQueries.getAllTransactionsForPositions(positionsIds);
    }
}
