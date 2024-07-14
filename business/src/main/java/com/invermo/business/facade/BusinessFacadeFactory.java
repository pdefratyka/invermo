package com.invermo.business.facade;

import com.invermo.business.service.ServiceManager;

public class BusinessFacadeFactory {
    public static OuterBusinessFacade createOuterBusinessFacade() {
        return new OuterBusinessFacade(
                ServiceManager.getAuthenticationService(),
                ServiceManager.getAssetService(),
                ServiceManager.getPositionService(),
                ServiceManager.getTransactionService(),
                ServiceManager.getTransactionDetailsCalculator(),
                ServiceManager.getPositionGainService());
    }
}
