package com.invermo.persistence.repository.transactions;

import com.invermo.persistence.entity.TransactionEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionsDatabaseQueries {

    static String saveTransaction(final TransactionEntity transaction) {
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
        return "select * from transactions where position_id in (" + getPositionsIdsSeparatedWithComa(positionsIds) + ") order by date";
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
