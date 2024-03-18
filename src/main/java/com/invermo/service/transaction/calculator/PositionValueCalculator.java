package com.invermo.service.transaction.calculator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PositionValueCalculator {

    public static BigDecimal getPositionValue(final BigDecimal numberOfAsset, final BigDecimal singleAssetValue, final BigDecimal currencyExchange) {
        return numberOfAsset.multiply(singleAssetValue).multiply(currencyExchange);
    }
}
