package com.invermo.business.service;

import com.invermo.business.domain.Transaction;
import com.invermo.business.service.persistence.TransactionPersistenceService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TransactionService {

    private final TransactionPersistenceService transactionPersistenceService;

    public void saveTransaction(final Transaction transaction) {
        transactionPersistenceService.saveTransaction(transaction);
    }

    public List<Transaction> getAllTransactionsForPositions(List<Long> positionIds) {
        if (positionIds.isEmpty()) {
            return List.of();
        }
        return transactionPersistenceService.getAllTransactionsForPositions(positionIds);
    }

    public List<Transaction> getAllTransactionForPosition(Long positionId) {
        return getAllTransactionsForPositions(List.of(positionId));
    }
}
