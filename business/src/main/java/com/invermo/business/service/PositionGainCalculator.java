package com.invermo.business.service;

import com.invermo.business.domain.Transaction;
import com.invermo.business.enumeration.TransactionType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PositionGainCalculator {

    public static BigDecimal getPositionGain(List<Transaction> transactions, final BigDecimal positionValue, final BigDecimal numberOfAssets) {
        final BigDecimal cost = getCost(transactions, numberOfAssets);
        return positionValue.subtract(cost);
    }

    public static BigDecimal getAllTimePositionGain(final List<Transaction> transactions, final BigDecimal positionValue) {
        final BigDecimal cost = getAllTimePositionCost(transactions);
        final BigDecimal valueOfSold = getValueOfSold(transactions);
        return positionValue.add(valueOfSold).subtract(cost);
    }

    public static BigDecimal getPercentageGain(final BigDecimal cost, final BigDecimal gain) {
        if (gain == null || gain.setScale(2, RoundingMode.FLOOR).equals(BigDecimal.valueOf(0).setScale(2, RoundingMode.FLOOR))
                || cost.setScale(2, RoundingMode.FLOOR).equals(BigDecimal.ZERO.setScale(2, RoundingMode.FLOOR))) {
            return BigDecimal.ZERO;
        }
        return gain.divide(cost, 4, RoundingMode.FLOOR).multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.FLOOR);
    }

    public static BigDecimal getCost(List<Transaction> transactions, final BigDecimal numberOfAssets) {
        BigDecimal currNumberOfAssets = BigDecimal.ZERO;
        BigDecimal currCost = BigDecimal.ZERO;
        transactions = transactions.stream()
                .filter(t -> TransactionType.BUY.equals(t.getTransactionType()))
                .sorted(Comparator.comparing(Transaction::getDateTime).reversed())
                .collect(Collectors.toList());
        for (Transaction t : transactions) {
            BigDecimal diff = numberOfAssets.subtract(currNumberOfAssets).subtract(t.getNumberOfAsset());

            if (diff.compareTo(BigDecimal.ZERO) >= 0) {
                currNumberOfAssets = currNumberOfAssets.add(t.getNumberOfAsset());
                currCost = currCost.add(t.getNumberOfAsset().multiply(t.getPrice()).multiply(t.getCurrencyExchangeRate()));
            } else {
                currCost = currCost.add(t.getNumberOfAsset().add(diff).multiply(t.getPrice()).multiply(t.getCurrencyExchangeRate()));
                break;
            }
        }
        return currCost;
    }

    private static BigDecimal getAllTimePositionCost(final List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> TransactionType.BUY.equals(t.getTransactionType()))
                .map(t -> t.getPrice().multiply(t.getNumberOfAsset()).multiply(t.getCurrencyExchangeRate()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static BigDecimal getValueOfSold(final List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> TransactionType.SELL.equals(t.getTransactionType()))
                .map(t -> t.getPrice().multiply(t.getNumberOfAsset()).multiply(t.getCurrencyExchangeRate()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
