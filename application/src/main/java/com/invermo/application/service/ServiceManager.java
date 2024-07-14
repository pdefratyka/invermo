package com.invermo.application.service;

import com.invermo.application.facade.InnerApplicationFacade;
import com.invermo.application.gui.facade.PositionDetailsFacade;
import com.invermo.application.service.impl.AssetPriceService;
import com.invermo.application.service.impl.AssetService;
import com.invermo.application.service.impl.AuthenticationService;
import com.invermo.application.service.impl.PortfolioService;
import com.invermo.application.service.impl.PositionService;
import com.invermo.application.service.impl.TransactionService;
import com.invermo.business.facade.BusinessFacadeFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceManager {

    private static InnerApplicationFacade innerApplicationFacade;
    private static AuthenticationService authenticationService;
    private static AssetService assetsService;
    private static PortfolioService portfolioService;
    private static PositionService positionService;
    private static TransactionService transactionService;
    private static AssetPriceService assetPriceService;
    private static PositionDetailsFacade positionDetailsFacade;

    public static AuthenticationService getAuthenticationService() {
        if (authenticationService == null) {
            authenticationService = new AuthenticationService(getInnerApplicationFacade());
        }
        return authenticationService;
    }

    public static AssetService getAssetsService() {
        if (assetsService == null) {
            assetsService = new AssetService(getInnerApplicationFacade());
        }
        return assetsService;
    }

    public static PortfolioService getPortfolioService() {
        if (portfolioService == null) {
            portfolioService = new PortfolioService(getAssetsService(), getPositionService(), getTransactionService());
        }
        return portfolioService;
    }

    public static PositionService getPositionService() {
        if (positionService == null) {
            positionService = new PositionService(getInnerApplicationFacade());
        }
        return positionService;
    }

    public static TransactionService getTransactionService() {
        if (transactionService == null) {
            transactionService = new TransactionService(getInnerApplicationFacade());
        }
        return transactionService;
    }

    public static AssetPriceService getAssetPriceService() {
        if (assetPriceService == null) {
            assetPriceService = new AssetPriceService(getAssetsService());
        }
        return assetPriceService;
    }

    public static PositionDetailsFacade getPositionDetailsFacade() {
        if (positionDetailsFacade == null) {
            positionDetailsFacade = new PositionDetailsFacade(getInnerApplicationFacade());
        }
        return positionDetailsFacade;
    }

    public static InnerApplicationFacade getInnerApplicationFacade() {
        if (innerApplicationFacade == null) {
            innerApplicationFacade = new InnerApplicationFacade(BusinessFacadeFactory.createOuterBusinessFacade());
        }
        return innerApplicationFacade;
    }
}
