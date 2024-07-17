package com.invermo.business.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@ToString
public class SinglePortfolioAsset {
    private String name;
    private String assetType;
    private String positionType;
    private Long positionId;
    private BigDecimal number;
    private BigDecimal price;
    private BigDecimal value;
    private BigDecimal gain;
    private BigDecimal percentageGain;
    private BigDecimal percentagePortfolioPart;
}
