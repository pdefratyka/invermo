package com.invermo.persistance.repository.impl.assets;

import com.invermo.persistance.entity.Asset;
import com.invermo.persistance.entity.AssetPrice;
import com.invermo.persistance.enumeration.AssetType;
import com.invermo.persistance.enumeration.Currency;
import com.invermo.persistance.repository.AbstractRepository;
import com.invermo.persistance.repository.AssetRepository;
import com.invermo.persistance.tables.AssetsTable;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AssetRepositoryImpl extends AbstractRepository implements AssetRepository {

    private static final Logger logger = Logger.getLogger(AssetRepositoryImpl.class.getName());

    @Override
    public List<Asset> getAllAssets() {
        final String query = prepareGetAssetsFromDbQuery();
        return executeQuery(query, this::extractAssetsFromResultSet);
    }

    @Override
    public List<Asset> searchAssets(String searchValue) {
        final String query = prepareSearchAssetsQuery(searchValue);
        return executeQuery(query, this::extractAssetsFromResultSet);
    }

    @Override
    public void saveAsset(final Asset asset) {
        String query;
        if (asset.assetId() != null) {
            query = prepareUpdateAssetQuery(asset);
        } else {
            query = prepareAddNewAssetQuery(asset);
        }
        execute(query);
    }

    @Override
    public void deleteAssetById(long id) {
        final String query = prepareRemoveAssetById(id);
        execute(query);
    }

    @Override
    public List<AssetPrice> getAssetWithPriceByAssetSymbol(String symbol) {
        final String query = prepareGetAssetWithPriceByAssetSymbol(symbol);
        return executeQuery(query, this::extractAssetsWithPrice);
    }

    @Override
    public List<AssetPrice> getLatestAssetsPrices() {
        final String query = prepareGetLatestAssetsPrices();
        return executeQuery(query, this::extractAssetsWithPrice);
    }

    @Override
    public BigDecimal getLatestAssetPrice(final Long assetId) {
        final String query = prepareGetLatestAssetPrice(assetId);
        return executeQuery(query, this::extractLatestAssetPrice);
    }

    @Override
    public void saveAssetPrices(final List<AssetPrice> assetPrices) {
        final List<String> queries = new ArrayList<>();
        for (AssetPrice assetPrice : assetPrices) {
            String query = prepareSaveAssetPriceQuery(assetPrice);
            queries.add(query);
        }
        executeQueriesInBatch(queries);
    }

    private List<AssetPrice> extractAssetsWithPrice(final ResultSet resultSet) {
        try {
            final List<AssetPrice> assetPrices = new ArrayList<>();
            while (resultSet.next()) {
                final AssetPrice assetPrice = buildAssetWithPriceFromResultSet(resultSet);
                assetPrices.add(assetPrice);
            }
            return assetPrices;
        } catch (SQLException ex) {
            throw new RuntimeException("Error during processing ResultSet", ex);
        }
    }

    private List<Asset> extractAssetsFromResultSet(final ResultSet resultSet) {
        try {
            final List<Asset> assets = new ArrayList<>();
            while (resultSet.next()) {
                final Asset asset = buildAssetFromResultSet(resultSet);
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

    private AssetPrice buildAssetWithPriceFromResultSet(final ResultSet resultSet) throws SQLException {
        final long assetId = resultSet.getLong(AssetsTable.ASSET_ID);
        final BigDecimal price = resultSet.getBigDecimal("price");
        final LocalDateTime dateTime = resultSet.getObject("date", LocalDateTime.class);
        return AssetPrice.builder()
                .assetId(assetId)
                .price(price)
                .dateTime(dateTime)
                .build();
    }

    private Asset buildAssetFromResultSet(final ResultSet resultSet) throws SQLException {
        final long assetId = resultSet.getLong(AssetsTable.ASSET_ID);
        final String name = resultSet.getString(AssetsTable.NAME);
        final String symbol = resultSet.getString(AssetsTable.SYMBOL);
        final String type = resultSet.getString(AssetsTable.TYPE);
        final String currency = resultSet.getString(AssetsTable.CURRENCY);
        return new Asset(assetId, name, symbol, AssetType.fromName(type), Currency.valueOf(currency));
    }

    private String prepareGetAssetsFromDbQuery() {
        return AssetsDatabaseQueries.getAllAssets();
    }

    private String prepareSearchAssetsQuery(String searchValue) {
        return AssetsDatabaseQueries.searchAssets(searchValue);
    }

    private String prepareAddNewAssetQuery(final Asset asset) {
        return AssetsDatabaseQueries.addNewAsset(asset);
    }

    private String prepareUpdateAssetQuery(final Asset asset) {
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

    private String prepareSaveAssetPriceQuery(final AssetPrice assetPrice) {
        return AssetsDatabaseQueries.saveAssetPrice(assetPrice);
    }

    private String prepareGetLatestAssetPrice(final Long assetId) {
        return AssetsDatabaseQueries.getLatestAssetPrice(assetId);
    }
}
