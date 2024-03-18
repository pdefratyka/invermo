package com.invermo.persistance.entity;

import com.invermo.persistance.enumeration.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transaction {
    private Long transactionId;
    private Long positionId;
    private LocalDateTime dateTime;
    private TransactionType transactionType;
    private BigDecimal price; // in original currency
    private BigDecimal spread;
    private BigDecimal swap; // Is not needed. It kind of does not make sense to mark swap
    private BigDecimal fee;
    private BigDecimal currencyExchangeRate;
    private BigDecimal numberOfAsset;
    private BigDecimal extraCosts;
    private String currency; // currency of asset for example dollar for nasdaq etf
    private String paidCurrency; // currency of payment for example PLN on xtb
}
