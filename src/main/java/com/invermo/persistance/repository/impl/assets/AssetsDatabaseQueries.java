package com.invermo.persistance.repository.impl.assets;

import com.invermo.persistance.entity.Asset;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AssetsDatabaseQueries {

    static String removeAssetById(final Long id) {
        return "DELETE FROM assets where asset_id = " + id;
    }

    static String updateAsset(final Asset asset) {
        return "UPDATE assets SET name='" + asset.name() + "', symbol='" + asset.symbol() + "', type='" + asset.type().getName() + "'" +
                " WHERE asset_id=" + asset.assetId();
    }

    static String addNewAsset(final Asset asset) {
        return "INSERT INTO assets (name, symbol, type)\n" +
                "VALUES ('" + asset.name() + "','" + asset.symbol() + "','" + asset.type().getName() + "')";
    }

    static String getAllAssets() {
        return "SELECT * FROM assets ORDER BY asset_id";
    }
}
