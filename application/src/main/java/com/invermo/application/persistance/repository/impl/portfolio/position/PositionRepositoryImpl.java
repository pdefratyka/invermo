package com.invermo.application.persistance.repository.impl.portfolio.position;

import com.invermo.application.persistance.entity.Position;
import com.invermo.application.persistance.entity.PositionWithAsset;
import com.invermo.application.persistance.enumeration.AssetType;
import com.invermo.application.persistance.enumeration.Currency;
import com.invermo.application.persistance.enumeration.PositionType;
import com.invermo.application.persistance.repository.AbstractRepository;
import com.invermo.application.persistance.repository.PositionRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PositionRepositoryImpl extends AbstractRepository implements PositionRepository {

    @Override
    public void savePosition(final Position position) {
        final String query = prepareAddNewPositionQuery(position);
        execute(query);
    }

    @Override
    public List<Position> getAllPositionsByUserId(Long userId) {
        final String query = prepareGetPositionsByUserIdQuery(userId);
        return executeQuery(query, this::extractPositionsFromResultSet);
    }

    @Override
    public List<PositionWithAsset> getPositionsWithAssetsForUser(Long userId) {
        final String query = prepareGetPositionsWithAssetsForUser(userId);
        return executeQuery(query, this::extractPositionsWithAssets);
    }

    @Override
    public void deletePositionById(Long positionId) {

    }

    private List<Position> extractPositionsFromResultSet(final ResultSet resultSet) {
        try {
            final List<Position> positions = new ArrayList<>();
            while (resultSet.next()) {
                final Position position = buildPositionFromResultSet(resultSet);
                positions.add(position);
            }
            return positions;
        } catch (SQLException ex) {
            throw new RuntimeException("Error during processing ResultSet", ex);
        }
    }

    private List<PositionWithAsset> extractPositionsWithAssets(final ResultSet resultSet) {
        try {
            final List<PositionWithAsset> positionsWithAssets = new ArrayList<>();
            while (resultSet.next()) {
                final PositionWithAsset positionWithAsset = buiildPositionWithAsset(resultSet);
                positionsWithAssets.add(positionWithAsset);
            }
            return positionsWithAssets;
        } catch (SQLException ex) {
            throw new RuntimeException("Error during processing ResultSet", ex);
        }
    }

    private Position buildPositionFromResultSet(final ResultSet resultSet) throws SQLException {
        final Long positionId = resultSet.getLong("position_id");
        final Long userId = resultSet.getLong("user_id");
        final Long assetId = resultSet.getLong("asset_id");
        final String positionType = resultSet.getString("position_type");
        return new Position(positionId, userId, assetId, PositionType.valueOf(positionType));
    }

    private PositionWithAsset buiildPositionWithAsset(final ResultSet resultSet) throws SQLException {
        final Long positionId = resultSet.getLong("position_id");
        final Long assetId = resultSet.getLong("asset_id");
        final String positionType = resultSet.getString("position_type");
        final String assetName = resultSet.getString("asset_name");
        final String assetSymbol = resultSet.getString("asset_symbol");
        final String assetType = resultSet.getString("asset_type");
        final String currency = resultSet.getString("currency");
        return PositionWithAsset.builder()
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

    private String prepareAddNewPositionQuery(final Position position) {
        return PositionsDatabaseQueries.addNewPosition(position);
    }

    private String prepareGetPositionsWithAssetsForUser(final Long userId) {
        return PositionsDatabaseQueries.getPositionsWithAssetsForUser(userId);
    }
}
