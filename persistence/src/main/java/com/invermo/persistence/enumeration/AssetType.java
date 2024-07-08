package com.invermo.persistence.enumeration;

import java.util.Arrays;

public enum AssetType {
    STOCK("Stock"),
    INDEX("Index"),
    CRYPTOCURRENCY("Cryptocurrency"),
    CURRENCY("Currency"),
    ETF("ETF");
    private final String name;

    AssetType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static AssetType fromName(String name) {
        return Arrays.stream(AssetType.values())
                .filter(assetType -> assetType.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Asset type not found for name: " + name));
    }
}
