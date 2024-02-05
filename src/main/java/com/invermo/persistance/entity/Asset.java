package com.invermo.persistance.entity;

import com.invermo.persistance.enumeration.AssetType;

public record Asset(Long assetId, String name, String symbol, AssetType type) {
}
