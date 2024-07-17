package com.invermo.application.service;

import com.invermo.application.facade.InnerApplicationFacade;
import com.invermo.application.gui.portfolio.service.PositionDetailsService;
import com.invermo.application.gui.statistics.service.StatisticsService;
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
    private static PositionDetailsService positionDetailsService;
    private static StatisticsService statisticsService;

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
            portfolioService = new PortfolioService(getInnerApplicationFacade());
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
            assetPriceService = new AssetPriceService(getInnerApplicationFacade());
        }
        return assetPriceService;
    }

    public static PositionDetailsService getPositionDetailsService() {
        if (positionDetailsService == null) {
            positionDetailsService = new PositionDetailsService(getInnerApplicationFacade());
        }
        return positionDetailsService;
    }

    public static InnerApplicationFacade getInnerApplicationFacade() {
        if (innerApplicationFacade == null) {
            innerApplicationFacade = new InnerApplicationFacade(BusinessFacadeFactory.createOuterBusinessFacade());
        }
        return innerApplicationFacade;
    }

    public static StatisticsService getStatisticsService() {
        if (statisticsService == null) {
            statisticsService = new StatisticsService(getInnerApplicationFacade());
        }
        return statisticsService;
    }
}
