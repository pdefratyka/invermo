package com.invermo.application.service;

import com.invermo.application.persistance.entity.Transaction;

import java.util.List;

public interface TransactionService {

    void saveTransaction(Transaction transaction);

    List<Transaction> getAllTransactionsForPositions(List<Long> positionIds);

    List<Transaction> getAllTransactionForPosition(Long positionId);
}
