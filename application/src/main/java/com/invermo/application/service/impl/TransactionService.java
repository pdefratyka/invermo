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
}
