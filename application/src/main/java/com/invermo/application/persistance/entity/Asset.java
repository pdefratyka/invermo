package com.invermo.application.persistance.entity;

import com.invermo.application.persistance.enumeration.AssetType;
import com.invermo.application.persistance.enumeration.Currency;

public record Asset(Long assetId, String name, String symbol, AssetType type, Currency currency) {
}
