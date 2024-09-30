package com.invermo.persistence.repository.assets;


import com.invermo.persistence.entity.AssetEntity;
import com.invermo.persistence.entity.AssetPriceEntity;
import com.invermo.persistence.enumeration.AssetType;
import com.invermo.persistence.enumeration.Currency;
import com.invermo.persistence.repository.AbstractRepository;
import com.invermo.persistence.tables.AssetsTable;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AssetRepository extends AbstractRepository {

    public List<AssetEntity> findAllAssets() {
        final String query = prepareGetAssetsFromDbQuery();
        return executeQuery(query, this::extractAssetsFromResultSet);
    }

    public List<AssetEntity> searchAssets(String searchValue) {
        final String query = prepareSearchAssetsQuery(searchValue);
        return executeQuery(query, this::extractAssetsFromResultSet);
    }

    public void saveAsset(final AssetEntity asset) {
        String query;
        if (asset.assetId() != null) {
            query = prepareUpdateAssetQuery(asset);
        } else {
            query = prepareAddNewAssetQuery(asset);
        }
        execute(query);
    }

    public void deleteAssetById(long id) {
        final String query = prepareRemoveAssetById(id);
        execute(query);
    }

    public List<AssetPriceEntity> getAssetWithPriceByAssetSymbol(String symbol) {
        final String query = prepareGetAssetWithPriceByAssetSymbol(symbol);
        return executeQuery(query, this::extractAssetsWithPrice);
    }

    public List<AssetPriceEntity> getLatestAssetsPrices() {
        final String query = prepareGetLatestAssetsPrices();
        return executeQuery(query, this::extractAssetsWithPrice);
    }

    public BigDecimal getLatestAssetPrice(final Long assetId) {
        final String query = prepareGetLatestAssetPrice(assetId);
        return executeQuery(query, this::extractLatestAssetPrice);
    }

    public void saveAssetPrices(final List<AssetPriceEntity> assetPrices) {
        final List<String> queries = new ArrayList<>();
        for (AssetPriceEntity assetPrice : assetPrices) {
            String query = prepareSaveAssetPriceQuery(assetPrice);
            queries.add(query);
        }
        executeQueriesInBatch(queries);
    }

    private List<AssetPriceEntity> extractAssetsWithPrice(final ResultSet resultSet) {
        try {
            final List<AssetPriceEntity> assetPrices = new ArrayList<>();
            while (resultSet.next()) {
                final AssetPriceEntity assetPrice = buildAssetWithPriceFromResultSet(resultSet);
                assetPrices.add(assetPrice);
            }
            return assetPrices;
        } catch (SQLException ex) {
            throw new RuntimeException("Error during processing ResultSet", ex);
        }
    }

    private List<AssetEntity> extractAssetsFromResultSet(final ResultSet resultSet) {
        try {
            final List<AssetEntity> assets = new ArrayList<>();
            while (resultSet.next()) {
                final AssetEntity asset = buildAssetFromResultSet(resultSet);
                assets.add(asset);
            }
            return assets;
        } catch (SQLException ex) {
            throw new RuntimeException("Error during processing ResultSet", ex);
        }
    }

    private BigDecimal extractLatestAssetPrice(final ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                return resultSet.getBigDecimal("price");
            }
            return BigDecimal.ZERO;
        } catch (SQLException ex) {
            throw new RuntimeException("Error during processing ResultSet", ex);
        }
    }

    private AssetPriceEntity buildAssetWithPriceFromResultSet(final ResultSet resultSet) throws SQLException {
        final long assetId = resultSet.getLong(AssetsTable.ASSET_ID);
        final BigDecimal price = resultSet.getBigDecimal("price");
        final LocalDateTime dateTime = resultSet.getObject("date", LocalDateTime.class);
        return AssetPriceEntity.builder()
                .assetId(assetId)
                .price(price)
                .dateTime(dateTime)
                .build();
    }

    private AssetEntity buildAssetFromResultSet(final ResultSet resultSet) throws SQLException {
        final long assetId = resultSet.getLong(AssetsTable.ASSET_ID);
        final String name = resultSet.getString(AssetsTable.NAME);
        final String symbol = resultSet.getString(AssetsTable.SYMBOL);
        final String type = resultSet.getString(AssetsTable.TYPE);
        final String currency = resultSet.getString(AssetsTable.CURRENCY);
        return new AssetEntity(assetId, name, symbol, AssetType.fromName(type), Currency.valueOf(currency));
    }

    private String prepareGetAssetsFromDbQuery() {
        return AssetsDatabaseQueries.getAllAssets();
    }

    private String prepareSearchAssetsQuery(String searchValue) {
        return AssetsDatabaseQueries.searchAssets(searchValue);
    }

    private String prepareAddNewAssetQuery(final AssetEntity asset) {
        return AssetsDatabaseQueries.addNewAsset(asset);
    }

    private String prepareUpdateAssetQuery(final AssetEntity asset) {
        return AssetsDatabaseQueries.updateAsset(asset);
    }

    private String prepareRemoveAssetById(final Long id) {
        return AssetsDatabaseQueries.removeAssetById(id);
    }

    private String prepareGetAssetWithPriceByAssetSymbol(final String symbol) {
        return AssetsDatabaseQueries.getAssetWithPriceByAssetSymbol(symbol);
    }

    private String prepareGetLatestAssetsPrices() {
        return AssetsDatabaseQueries.getLatestAssetsPrices();
    }

    private String prepareSaveAssetPriceQuery(final AssetPriceEntity assetPrice) {
        return AssetsDatabaseQueries.saveAssetPrice(assetPrice);
    }

    private String prepareGetLatestAssetPrice(final Long assetId) {
        return AssetsDatabaseQueries.getLatestAssetPrice(assetId);
    }
}
