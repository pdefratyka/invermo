package com.invermo.persistence.repository.assets;

import com.invermo.persistence.entity.AssetEntity;
import com.invermo.persistence.entity.AssetPriceEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AssetsDatabaseQueries {

    static String removeAssetById(final Long id) {
        return "DELETE FROM assets where asset_id = " + id;
    }

    static String updateAsset(final AssetEntity asset) {
        return "UPDATE assets SET name='" + asset.name() + "'," +
                " symbol='" + asset.symbol() + "'," +
                " type='" + asset.type().getName() + "'," +
                " currency='" + asset.currency().toString() + "'" +
                " WHERE asset_id=" + asset.assetId();
    }

    static String addNewAsset(final AssetEntity asset) {
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

    static String getAssetWithPriceByAssetSymbol(final String symbol) {
        return "select ap.date as date, ap.price as price, ap.asset_id as asset_id " +
                "from asset_price ap right join invermo.assets a on ap.asset_id=a.asset_id " +
                "where a.symbol='" + symbol + "'";
    }

    static String getLatestAssetsPrices() {
        return "SELECT ap.asset_id, ap.price, ap.date " +
                "FROM invermo.asset_price ap " +
                "INNER JOIN ( " +
                "SELECT asset_id, MAX(date) AS latest_date " +
                "FROM invermo.asset_price " +
                "GROUP BY asset_id) " +
                "latest_dates ON ap.asset_id = latest_dates.asset_id AND ap.date = latest_dates.latest_date;";
    }

    static String getLatestAssetPrice(final Long assetId) {
        return "SELECT ap.price as price " +
                "FROM invermo.asset_price ap " +
                "WHERE ap.asset_id = " + assetId + " " +
                "ORDER BY date DESC " +
                "LIMIT 1";

    }

    static String saveAssetPrice(final AssetPriceEntity assetPrice) {
        return "INSERT INTO asset_price (asset_id, price, date)\n" +
                "VALUES ('" +
                assetPrice.getAssetId() + "','" +
                assetPrice.getPrice() + "','" +
                assetPrice.getDateTime().toString() + "')";
    }
}
