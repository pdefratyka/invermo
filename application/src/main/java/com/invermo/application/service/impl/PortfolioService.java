package com.invermo.application.service.impl;

import com.invermo.application.facade.InnerApplicationFacade;
import com.invermo.application.state.ApplicationState;
import com.invermo.business.domain.SinglePortfolioAsset;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PortfolioService {

    private final InnerApplicationFacade innerApplicationFacade;

    public List<SinglePortfolioAsset> getPortfolioAssets() {
        final Long userId = ApplicationState.getUser().id();
        return innerApplicationFacade.getPortfolioAssets(userId);
    }
}
