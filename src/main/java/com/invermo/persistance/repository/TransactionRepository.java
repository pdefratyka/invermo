package com.invermo.persistance.repository;

import com.invermo.persistance.entity.Transaction;

import java.util.List;

public interface TransactionRepository {
    void saveTransaction(Transaction transaction);

    List<Transaction> getAllTransactionsForPositions(List<Long> positionsIds);
}
