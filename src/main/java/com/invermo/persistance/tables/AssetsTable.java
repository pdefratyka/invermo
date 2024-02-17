package com.invermo.persistance.tables;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AssetsTable {

    public static final String ASSET_ID = "asset_id";
    public static final String NAME = "name";
    public static final String SYMBOL = "symbol";
    public static final String TYPE = "type";
    public static final String CURRENCY = "currency";
}
