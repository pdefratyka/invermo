package com.invermo.gui.facade;

import com.invermo.gui.portfolio.dto.SingleTransactionRecord;
import com.invermo.service.transaction.calculator.TransactionDetailsCalculator;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PositionDetailsFacade {

    private final TransactionDetailsCalculator transactionDetailsCalculator;

    public List<SingleTransactionRecord> getSingleTransactionsForPosition(final Long positionId) {
        return transactionDetailsCalculator.getSingleTransactionsForPosition(positionId);
    }
}
