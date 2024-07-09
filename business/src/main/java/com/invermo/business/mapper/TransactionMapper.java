package com.invermo.business.mapper;

import com.invermo.business.domain.Transaction;
import com.invermo.business.enumeration.TransactionType;
import com.invermo.persistence.entity.TransactionEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TransactionMapper {

    public static TransactionEntity mapTransactionToTransactionEntity(final Transaction transaction) {
        return TransactionEntity.builder()
                .positionId(transaction.getPositionId())
                .transactionId(transaction.getTransactionId())
                .transactionType(com.invermo.persistence.enumeration.TransactionType.valueOf(transaction.getTransactionType().name()))
                .dateTime(transaction.getDateTime())
                .price(transaction.getPrice())
                .swap(transaction.getSwap())
                .spread(transaction.getSpread())
                .fee(transaction.getFee())
                .currencyExchangeRate(transaction.getCurrencyExchangeRate())
                .numberOfAsset(transaction.getNumberOfAsset())
                .extraCosts(transaction.getExtraCosts())
                .currency(transaction.getCurrency())
                .paidCurrency(transaction.getPaidCurrency())
                .build();
    }

    public static List<Transaction> mapPositionEntitiesToPositions(final List<TransactionEntity> transactionEntities) {
        final List<Transaction> transactions = new ArrayList<>();
        for (TransactionEntity transactionEntity : transactionEntities) {
            Transaction transaction = Transaction.builder()
                    .positionId(transactionEntity.getPositionId())
                    .transactionId(transactionEntity.getTransactionId())
                    .transactionType(TransactionType.valueOf(transactionEntity.getTransactionType().name()))
                    .dateTime(transactionEntity.getDateTime())
                    .price(transactionEntity.getPrice())
                    .swap(transactionEntity.getSwap())
                    .spread(transactionEntity.getSpread())
                    .fee(transactionEntity.getFee())
                    .currencyExchangeRate(transactionEntity.getCurrencyExchangeRate())
                    .numberOfAsset(transactionEntity.getNumberOfAsset())
                    .extraCosts(transactionEntity.getExtraCosts())
                    .currency(transactionEntity.getCurrency())
                    .paidCurrency(transactionEntity.getPaidCurrency())
                    .build();
            transactions.add(transaction);
        }
        return transactions;
    }
}
