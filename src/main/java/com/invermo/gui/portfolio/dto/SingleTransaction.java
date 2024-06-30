package com.invermo.gui.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@ToString
public class SingleTransaction {
    private Long transactionId;
    private LocalDateTime dateTime;
    private String transactionType;
    private BigDecimal number;
    private String pricePerOne;
    private String price;
    private String value;
    private BigDecimal gain;
    private BigDecimal percentageGain;
    private BigDecimal percentageAssetPart;
}
