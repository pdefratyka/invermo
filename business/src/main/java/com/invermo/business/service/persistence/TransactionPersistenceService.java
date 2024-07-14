package com.invermo.business.service.persistence;

import com.invermo.business.domain.Transaction;
import com.invermo.business.facade.InnerBusinessFacade;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TransactionPersistenceService {

    private final InnerBusinessFacade innerBusinessFacade;

    public void saveTransaction(final Transaction transaction) {
        innerBusinessFacade.saveTransaction(transaction);
    }

    public List<Transaction> getAllTransactionsForPositions(List<Long> positionIds) {
        return innerBusinessFacade.getAllTransactionsForPositions(positionIds);
    }
}
