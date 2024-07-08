package com.invermo.business.domain;

import com.invermo.business.enumeration.AssetType;
import com.invermo.business.enumeration.Currency;

public record Asset(Long assetId, String name, String symbol, AssetType type, Currency currency) {
}
