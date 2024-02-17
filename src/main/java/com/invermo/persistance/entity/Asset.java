package com.invermo.persistance.entity;

import com.invermo.persistance.enumeration.AssetType;
import com.invermo.persistance.enumeration.Currency;

public record Asset(Long assetId, String name, String symbol, AssetType type, Currency currency) {
}
