package com.invermo.gui.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class SinglePortfolioAsset {
    private String name;
    private String assetType;
    private String positionType;
    private BigDecimal number;
    private BigDecimal price;
    private BigDecimal value;
    private BigDecimal gain;
    private BigDecimal percentageGain;
    private BigDecimal percentagePortfolioPart;
}
