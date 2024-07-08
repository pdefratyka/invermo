package com.invermo.application.persistance.repository;

import com.invermo.application.persistance.entity.Position;
import com.invermo.application.persistance.entity.PositionWithAsset;

import java.util.List;

public interface PositionRepository {
    void savePosition(Position position);

    List<Position> getAllPositionsByUserId(Long userId);

    List<PositionWithAsset> getPositionsWithAssetsForUser(Long userId);

    void deletePositionById(Long positionId);
}
