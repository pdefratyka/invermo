package com.invermo.application.service.impl;

import com.invermo.application.facade.InnerApplicationFacade;
import com.invermo.application.state.ApplicationState;
import com.invermo.business.domain.Position;
import com.invermo.business.domain.PositionWithAsset;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PositionService {

    private final InnerApplicationFacade innerApplicationFacade;

    public void addNewPosition(final Position position) {
        final Long userId = ApplicationState.getUser().id();
        innerApplicationFacade.addNewPosition(position, userId);
    }

    public List<PositionWithAsset> getPositionsWithAssetsForUser() {
        final Long userId = ApplicationState.getUser().id();
        return innerApplicationFacade.getPositionsWithAssetsForUser(userId);
    }
}
