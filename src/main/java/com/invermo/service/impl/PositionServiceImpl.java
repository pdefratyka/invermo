package com.invermo.service.impl;

import com.invermo.persistance.entity.Position;
import com.invermo.persistance.entity.PositionWithAsset;
import com.invermo.persistance.repository.PositionRepository;
import com.invermo.service.PositionService;
import com.invermo.state.ApplicationState;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;

    @Override
    public List<Position> getAllPositionsForUser() {
        final Long userId = ApplicationState.getUser().id();
        return positionRepository.getAllPositionsByUserId(userId);
    }

    @Override
    public Position getPositionById(Long positionId) {
        List<Position> positions = getAllPositionsForUser();
        return positions.stream().filter(position -> position.getId().equals(positionId))
                .findFirst().orElseThrow(() -> new RuntimeException("No position with given id found"));
    }

    @Override
    public void addNewPosition(final Position position) {
        final Long userId = ApplicationState.getUser().id();
        position.setUserId(userId);
        positionRepository.savePosition(position);
    }

    @Override
    public List<PositionWithAsset> getPositionsWithAssetsForUser() {
        final Long userId = ApplicationState.getUser().id();
        return positionRepository.getPositionsWithAssetsForUser(userId);
    }
}
