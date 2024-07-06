package com.invermo.gui.portfolio.dto;

import com.invermo.persistance.entity.Transaction;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
public class SingleTransactionRecord {
    private Transaction transaction;
    private BigDecimal pricePerOne;
    private BigDecimal price;
    private BigDecimal numberOfSold;
    private BigDecimal numberOfActive;
    private BigDecimal realizedGain;
    private BigDecimal activeGain;
    private BigDecimal allGain;
    private BigDecimal percentageGain;
    private BigDecimal part;
    private boolean isActive;

    public static SingleTransactionRecord of(final Transaction transaction) {
        final SingleTransactionRecord singleTransactionRecord = new SingleTransactionRecord();
        singleTransactionRecord.setTransaction(transaction);
        singleTransactionRecord.setPricePerOne(transaction.getPrice().multiply(transaction.getCurrencyExchangeRate()));
        singleTransactionRecord.setPrice(transaction.getPrice().multiply(transaction.getCurrencyExchangeRate()).multiply(transaction.getNumberOfAsset()));
        singleTransactionRecord.setNumberOfSold(BigDecimal.ZERO);
        singleTransactionRecord.setNumberOfActive(BigDecimal.ZERO);
        singleTransactionRecord.setRealizedGain(BigDecimal.ZERO);
        singleTransactionRecord.setActiveGain(BigDecimal.ZERO);
        singleTransactionRecord.setAllGain(BigDecimal.ZERO);
        singleTransactionRecord.setPercentageGain(BigDecimal.ZERO);
        singleTransactionRecord.setPart(BigDecimal.ZERO);
        singleTransactionRecord.setActive(true);
        return singleTransactionRecord;
    }
}
