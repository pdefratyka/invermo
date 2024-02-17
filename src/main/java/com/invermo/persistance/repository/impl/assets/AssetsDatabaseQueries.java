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
        return "UPDATE assets SET name='" + asset.name() + "'," +
                " symbol='" + asset.symbol() + "'," +
                " type='" + asset.type().getName() + "'," +
                " currency='" + asset.currency().toString() + "'" +
                " WHERE asset_id=" + asset.assetId();
    }

    static String addNewAsset(final Asset asset) {
        return "INSERT INTO assets (name, symbol, type, currency)\n" +
                "VALUES ('" +
                asset.name() + "','" +
                asset.symbol() + "','" +
                asset.type().getName() + "','" +
                asset.currency().toString() + "')";
    }

    static String getAllAssets() {
        return "SELECT * FROM assets ORDER BY asset_id";
    }

    static String searchAssets(final String searchValue) {
        return "SELECT * FROM assets WHERE " +
                "name ilike '%" + searchValue + "%' " +
                "or symbol ilike '%" + searchValue + "%' " +
                "or type ilike '%" + searchValue + "%' " +
                "ORDER BY asset_id";
    }
}
