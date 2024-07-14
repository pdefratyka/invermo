package com.invermo.business.service.persistence;

import com.invermo.business.domain.Position;
import com.invermo.business.domain.PositionWithAsset;
import com.invermo.business.facade.InnerBusinessFacade;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PositionPersistenceService {

    private final InnerBusinessFacade innerBusinessFacade;

    public List<Position> getAllPositionsForUser(final Long userId) {
        return innerBusinessFacade.getAllPositionsByUserId(userId);
    }

    public PositionWithAsset getPositionWithAssetByPositionId(final Long positionId) {
        return innerBusinessFacade.getPositionWithAssetByPositionId(positionId);
    }

    public void addNewPosition(final Position position) {
        innerBusinessFacade.savePosition(position);
    }

    public List<PositionWithAsset> getPositionsWithAssetsForUser(final Long userId) {
        return innerBusinessFacade.getPositionsWithAssetsForUser(userId);
    }
}
