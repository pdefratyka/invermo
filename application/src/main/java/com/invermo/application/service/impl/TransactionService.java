package com.invermo.application.service.impl;

import com.invermo.application.facade.InnerApplicationFacade;
import com.invermo.business.domain.Transaction;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TransactionService {

    private final InnerApplicationFacade innerApplicationFacade;

    public void saveTransaction(final Transaction transaction) {
        innerApplicationFacade.saveTransaction(transaction);
    }

    public List<Transaction> getAllTransactionsForPositions(List<Long> positionIds) {
        return innerApplicationFacade.getAllTransactionsForPositions(positionIds);
    }

    public List<Transaction> getAllTransactionForPosition(Long positionId) {
        return getAllTransactionsForPositions(List.of(positionId));
    }
}
