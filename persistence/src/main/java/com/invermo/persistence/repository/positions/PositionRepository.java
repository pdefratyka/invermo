package com.invermo.persistence.repository.positions;


import com.invermo.persistence.entity.PositionEntity;
import com.invermo.persistence.entity.PositionWithAssetEntity;
import com.invermo.persistence.enumeration.AssetType;
import com.invermo.persistence.enumeration.Currency;
import com.invermo.persistence.enumeration.PositionType;
import com.invermo.persistence.repository.AbstractRepository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PositionRepository extends AbstractRepository {

    public void savePosition(final PositionEntity position) {
        final String query = prepareAddNewPositionQuery(position);
        execute(query);
    }

    public PositionWithAssetEntity getPositionWithAssetByPositionId(Long positionId) {
        final String query = prepareGetPositionWithAssetByPositionIdQuery(positionId);
        return executeQuery(query, this::extractPositionWithAssetEntityFromResultSet);
    }

    public List<PositionEntity> getAllPositionsByUserId(Long userId) {
        final String query = prepareGetPositionsByUserIdQuery(userId);
        return executeQuery(query, this::extractPositionsFromResultSet);
    }

    public List<PositionWithAssetEntity> getPositionsWithAssetsForUser(Long userId) {
        final String query = prepareGetPositionsWithAssetsForUser(userId);
        return executeQuery(query, this::extractPositionsWithAssets);
    }

    private List<PositionEntity> extractPositionsFromResultSet(final ResultSet resultSet) {
        try {
            final List<PositionEntity> positions = new ArrayList<>();
            while (resultSet.next()) {
                final PositionEntity position = buildPositionFromResultSet(resultSet);
                positions.add(position);
            }
            return positions;
        } catch (SQLException ex) {
            throw new RuntimeException("Error during processing ResultSet", ex);
        }
    }

    private PositionWithAssetEntity extractPositionWithAssetEntityFromResultSet(final ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                return buildPositionWithAsset(resultSet);
            }
            return null;
        } catch (SQLException ex) {
            throw new RuntimeException("Error during processing ResultSet", ex);
        }
    }

    private List<PositionWithAssetEntity> extractPositionsWithAssets(final ResultSet resultSet) {
        try {
            final List<PositionWithAssetEntity> positionsWithAssets = new ArrayList<>();
            while (resultSet.next()) {
                final PositionWithAssetEntity positionWithAsset = buildPositionWithAsset(resultSet);
                positionsWithAssets.add(positionWithAsset);
            }
            return positionsWithAssets;
        } catch (SQLException ex) {
            throw new RuntimeException("Error during processing ResultSet", ex);
        }
    }

    private PositionEntity buildPositionFromResultSet(final ResultSet resultSet) throws SQLException {
        final Long positionId = resultSet.getLong("position_id");
        final Long userId = resultSet.getLong("user_id");
        final Long assetId = resultSet.getLong("asset_id");
        final String positionType = resultSet.getString("position_type");
        return new PositionEntity(positionId, userId, assetId, PositionType.valueOf(positionType));
    }

    private PositionWithAssetEntity buildPositionWithAsset(final ResultSet resultSet) throws SQLException {
        final Long positionId = resultSet.getLong("position_id");
        final Long assetId = resultSet.getLong("asset_id");
        final String positionType = resultSet.getString("position_type");
        final String assetName = resultSet.getString("asset_name");
        final String assetSymbol = resultSet.getString("asset_symbol");
        final String assetType = resultSet.getString("asset_type");
        final String currency = resultSet.getString("currency");
        return PositionWithAssetEntity.builder()
                .positionId(positionId)
                .assetId(assetId)
                .positionType(PositionType.valueOf(positionType))
                .assetName(assetName)
                .assetSymbol(assetSymbol)
                .assetType(AssetType.fromName(assetType))
                .currency(Currency.valueOf(currency))
                .build();
    }

    private String prepareGetPositionsByUserIdQuery(final Long userId) {
        return PositionsDatabaseQueries.getAllPositionsForUser(userId);
    }

    private String prepareGetPositionWithAssetByPositionIdQuery(final Long positionId) {
        return PositionsDatabaseQueries.getPositionWithAssetByPositionId(positionId);
    }

    private String prepareAddNewPositionQuery(final PositionEntity position) {
        return PositionsDatabaseQueries.addNewPosition(position);
    }

    private String prepareGetPositionsWithAssetsForUser(final Long userId) {
        return PositionsDatabaseQueries.getPositionsWithAssetsForUser(userId);
    }
}
