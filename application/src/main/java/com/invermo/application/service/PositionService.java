package com.invermo.application.service;

import com.invermo.application.persistance.entity.Position;
import com.invermo.application.persistance.entity.PositionWithAsset;

import java.util.List;

public interface PositionService {

    List<Position> getAllPositionsForUser();

    Position getPositionById(Long positionId);

    void addNewPosition(Position position);

    List<PositionWithAsset> getPositionsWithAssetsForUser();
}
