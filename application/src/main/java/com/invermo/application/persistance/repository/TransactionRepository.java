package com.invermo.application.persistance.repository;

import com.invermo.application.persistance.entity.Transaction;

import java.util.List;

public interface TransactionRepository {
    void saveTransaction(Transaction transaction);

    List<Transaction> getAllTransactionsForPositions(List<Long> positionsIds);
}
