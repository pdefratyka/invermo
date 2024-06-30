package com.invermo.service.impl;

import com.invermo.persistance.entity.Transaction;
import com.invermo.persistance.repository.TransactionRepository;
import com.invermo.service.TransactionService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(final Transaction transaction) {
        transactionRepository.saveTransaction(transaction);
    }

    public List<Transaction> getAllTransactionsForPositions(List<Long> positionIds) {
        if (positionIds.isEmpty()) {
            return List.of();
        }
        return transactionRepository.getAllTransactionsForPositions(positionIds);
    }

    @Override
    public List<Transaction> getAllTransactionForPosition(Long positionId) {
        return getAllTransactionsForPositions(List.of(positionId));
    }
}
