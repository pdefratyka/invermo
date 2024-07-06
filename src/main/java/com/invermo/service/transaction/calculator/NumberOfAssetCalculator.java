package com.invermo.service.transaction.calculator;

import com.invermo.persistance.entity.Transaction;
import com.invermo.persistance.enumeration.TransactionType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NumberOfAssetCalculator {

    public static BigDecimal getNumberOfAsset(final List<Transaction> transactions) {
        final BigDecimal numberOfBoughtAssets = getNumberOfBoughtAssets(transactions);
        final BigDecimal numberOfSoldAssets = getNumberOfSoldAssets(transactions);
        final BigDecimal numberOfAsset = numberOfBoughtAssets.subtract(numberOfSoldAssets);
        return numberOfAsset.setScale(4, RoundingMode.FLOOR);
    }

    private static BigDecimal getNumberOfBoughtAssets(final List<Transaction> transactions) {
        return transactions.stream()
                .filter(transaction -> TransactionType.BUY.equals(transaction.getTransactionType()))
                .map(Transaction::getNumberOfAsset)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static BigDecimal getNumberOfSoldAssets(final List<Transaction> transactions) {
        return transactions.stream()
                .filter(transaction -> TransactionType.SELL.equals(transaction.getTransactionType()))
                .map(Transaction::getNumberOfAsset)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
