package com.invermo.application.gui.facade;

import com.invermo.application.gui.portfolio.dto.SingleTransactionRecord;
import com.invermo.application.service.transaction.calculator.TransactionDetailsCalculator;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PositionDetailsFacade {

    private final TransactionDetailsCalculator transactionDetailsCalculator;

    public List<SingleTransactionRecord> getSingleTransactionsForPosition(final Long positionId) {
        return transactionDetailsCalculator.getSingleTransactionsForPosition(positionId);
    }
}
