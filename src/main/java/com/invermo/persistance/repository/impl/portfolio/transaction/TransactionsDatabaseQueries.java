package com.invermo.persistance.repository.impl.portfolio.transaction;

import com.invermo.persistance.entity.Transaction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionsDatabaseQueries {

    static String saveTransaction(final Transaction transaction) {
        return "INSERT INTO transactions (position_id, date, number_of_asset, price_per_asset, currency_exchange, transaction_type)\n" +
                "VALUES ('" +
                transaction.getPositionId() + "','" +
                transaction.getDateTime() + "','" +
                transaction.getNumberOfAsset() + "','" +
                transaction.getPrice() + "','" +
                transaction.getCurrencyExchangeRate() + "','" +
                transaction.getTransactionType().toString() + "')";
    }

    static String getAllTransactionsForPositions(final List<Long> positionsIds) {
        return "select * from transactions where position_id in (" + getPositionsIdsSeparatedWithComa(positionsIds) + ")";
    }

    private static String getPositionsIdsSeparatedWithComa(final List<Long> positionsId) {
        StringBuilder result = new StringBuilder();
        for (Long id : positionsId) {
            result.append(id).append(",");
        }

        if (!positionsId.isEmpty()) {
            return result.substring(0, result.length() - 1);
        }

        return "";
    }
}
