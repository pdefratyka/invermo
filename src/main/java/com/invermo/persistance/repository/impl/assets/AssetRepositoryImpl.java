package com.invermo.persistance.repository.impl.assets;

import com.invermo.persistance.entity.Asset;
import com.invermo.persistance.enumeration.AssetType;
import com.invermo.persistance.repository.AbstractRepository;
import com.invermo.persistance.repository.AssetRepository;
import com.invermo.persistance.tables.AssetsTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssetRepositoryImpl extends AbstractRepository implements AssetRepository {

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

    private Asset buildAssetFromResultSet(final ResultSet resultSet) throws SQLException {
        final long assetId = resultSet.getLong(AssetsTable.ASSET_ID);
        final String name = resultSet.getString(AssetsTable.NAME);
        final String symbol = resultSet.getString(AssetsTable.SYMBOL);
        final String type = resultSet.getString(AssetsTable.TYPE);
        return new Asset(assetId, name, symbol, AssetType.fromName(type));
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
}
