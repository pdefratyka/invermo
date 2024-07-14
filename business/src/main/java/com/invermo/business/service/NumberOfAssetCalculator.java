package com.invermo.business.service;

import com.invermo.business.domain.Transaction;
import com.invermo.business.enumeration.TransactionType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NumberOfAssetCalculator {

    public static BigDecimal getNumberOfAsset(final List<Transaction> transactions) {
        final BigDecimal numberOfBoughtAssets = getNumberOfBoughtAssets(transactions, LocalDateTime.now());
        final BigDecimal numberOfSoldAssets = getNumberOfSoldAssets(transactions, LocalDateTime.now());
        final BigDecimal numberOfAsset = numberOfBoughtAssets.subtract(numberOfSoldAssets);
        return numberOfAsset.setScale(4, RoundingMode.FLOOR);
    }

    public static BigDecimal getNumberOfAssetBeforeDate(final List<Transaction> transactions, final LocalDateTime localDateTime) {
        final BigDecimal numberOfBoughtAssets = getNumberOfBoughtAssets(transactions, localDateTime);
        final BigDecimal numberOfSoldAssets = getNumberOfSoldAssets(transactions, localDateTime);
        final BigDecimal numberOfAsset = numberOfBoughtAssets.subtract(numberOfSoldAssets);
        return numberOfAsset.setScale(4, RoundingMode.FLOOR);
    }

    private static BigDecimal getNumberOfBoughtAssets(final List<Transaction> transactions, final LocalDateTime localDateTime) {
        return transactions.stream()
                .filter(transaction -> TransactionType.BUY.equals(transaction.getTransactionType()))
                .filter(transaction -> transaction.getDateTime().isBefore(localDateTime))
                .map(Transaction::getNumberOfAsset)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static BigDecimal getNumberOfSoldAssets(final List<Transaction> transactions, final LocalDateTime localDateTime) {
        return transactions.stream()
                .filter(transaction -> TransactionType.SELL.equals(transaction.getTransactionType()))
                .filter(transaction -> transaction.getDateTime().isBefore(localDateTime))
                .map(Transaction::getNumberOfAsset)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
