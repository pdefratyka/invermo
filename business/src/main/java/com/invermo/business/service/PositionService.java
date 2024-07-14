package com.invermo.business.service;

import com.invermo.business.domain.Position;
import com.invermo.business.domain.PositionWithAsset;
import com.invermo.business.service.persistence.PositionPersistenceService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PositionService {

    private final PositionPersistenceService positionPersistenceService;

    public List<Position> getAllPositionsForUser(final Long userId) {
        return positionPersistenceService.getAllPositionsForUser(userId);
    }

    public Position getPositionById(final Long positionId, final Long userId) {
        List<Position> positions = getAllPositionsForUser(userId);
        return positions.stream().filter(position -> position.getId().equals(positionId))
                .findFirst().orElseThrow(() -> new RuntimeException("No position with given id found"));
    }

    public void addNewPosition(final Position position, final Long userId) {
        position.setUserId(userId);
        positionPersistenceService.addNewPosition(position);
    }

    public PositionWithAsset getPositionWithAssetByPositionId(final Long positionId) {
        return positionPersistenceService.getPositionWithAssetByPositionId(positionId);
    }

    public List<PositionWithAsset> getPositionsWithAssetsForUser(final Long userId) {
        return positionPersistenceService.getPositionsWithAssetsForUser(userId);
    }
}
