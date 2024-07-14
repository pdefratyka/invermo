package com.invermo.persistence.entity;

import com.invermo.persistence.enumeration.AssetType;
import com.invermo.persistence.enumeration.Currency;

public record AssetEntity(Long assetId, String name, String symbol, AssetType type, Currency currency) {
}
