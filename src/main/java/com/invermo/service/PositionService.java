package com.invermo.service;

import com.invermo.persistance.entity.Position;
import com.invermo.persistance.entity.PositionWithAsset;

import java.util.List;

public interface PositionService {

    List<Position> getAllPositionsForUser();

    void addNewPosition(Position position);

    List<PositionWithAsset> getPositionsWithAssetsForUser();
}
