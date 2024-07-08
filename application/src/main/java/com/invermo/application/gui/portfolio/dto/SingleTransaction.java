package com.invermo.application.gui.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    private String number;
    private String pricePerOne;
    private String price;
    private String numberOfSold;
    private String numberOfActive;
    private String realizedGain;
    private String activeGain;
    private String allGain;
    private String percentageGain;
    private String part;
    private boolean isActive;
}
