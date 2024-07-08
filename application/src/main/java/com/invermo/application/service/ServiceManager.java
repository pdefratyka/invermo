package com.invermo.application.service;

import com.invermo.application.facade.InnerApplicationFacade;
import com.invermo.application.gui.facade.PositionDetailsFacade;
import com.invermo.application.persistance.repository.PortfolioRepository;
import com.invermo.application.persistance.repository.PositionRepository;
import com.invermo.application.persistance.repository.TransactionRepository;
import com.invermo.application.persistance.repository.impl.portfolio.PortfolioRepositoryImpl;
import com.invermo.application.persistance.repository.impl.portfolio.position.PositionRepositoryImpl;
import com.invermo.application.persistance.repository.impl.portfolio.transaction.TransactionRepositoryImpl;
import com.invermo.application.service.impl.AssetPriceServiceImpl;
import com.invermo.application.service.impl.AssetsServiceImpl;
import com.invermo.application.service.impl.AuthenticationServiceImpl;
import com.invermo.application.service.impl.PortfolioServiceImpl;
import com.invermo.application.service.impl.PositionServiceImpl;
import com.invermo.application.service.impl.TransactionServiceImpl;
import com.invermo.application.service.transaction.calculator.TransactionDetailsCalculator;
import com.invermo.business.facade.BusinessFacadeFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceManager {

    private static InnerApplicationFacade innerApplicationFacade;
    private static AuthenticationService authenticationService;
    private static PortfolioRepository portfolioRepository;
    private static AssetsService assetsService;
    private static PortfolioService portfolioService;
    private static PositionService positionService;
    private static PositionRepository positionRepository;
    private static TransactionRepository transactionRepository;
    private static TransactionService transactionService;
    private static AssetPriceService assetPriceService;
    private static PositionDetailsFacade positionDetailsFacade;
    private static TransactionDetailsCalculator transactionDetailsCalculator;

    public static AuthenticationService getAuthenticationService() {
        if (authenticationService == null) {
            authenticationService = new AuthenticationServiceImpl(getInnerApplicationFacade());
        }
        return authenticationService;
    }

    public static AssetsService getAssetsService() {
        if (assetsService == null) {
            assetsService = new AssetsServiceImpl(getInnerApplicationFacade());
        }
        return assetsService;
    }

    public static PortfolioService getPortfolioService() {
        if (portfolioService == null) {
            portfolioService = new PortfolioServiceImpl(getAssetsService(), getPositionService(), getTransactionService());
        }
        return portfolioService;
    }

    public static PositionService getPositionService() {
        if (positionService == null) {
            positionService = new PositionServiceImpl(getPositionRepository());
        }
        return positionService;
    }

    public static TransactionService getTransactionService() {
        if (transactionService == null) {
            transactionService = new TransactionServiceImpl(getTransactionRepository());
        }
        return transactionService;
    }

    public static AssetPriceService getAssetPriceService() {
        if (assetPriceService == null) {
            assetPriceService = new AssetPriceServiceImpl(getAssetsService());
        }
        return assetPriceService;
    }

    public static TransactionDetailsCalculator getTransactionDetailsCalculator() {
        if (transactionDetailsCalculator == null) {
            transactionDetailsCalculator = new TransactionDetailsCalculator(getTransactionService(), getAssetsService(), getPositionService());
        }
        return transactionDetailsCalculator;
    }

    public static PositionDetailsFacade getPositionDetailsFacade() {
        if (positionDetailsFacade == null) {
            positionDetailsFacade = new PositionDetailsFacade(getTransactionDetailsCalculator());
        }
        return positionDetailsFacade;
    }

    private static PortfolioRepository getPortfolioRepository() {
        if (portfolioRepository == null) {
            portfolioRepository = new PortfolioRepositoryImpl();
        }
        return portfolioRepository;
    }

    private static PositionRepository getPositionRepository() {
        if (positionRepository == null) {
            positionRepository = new PositionRepositoryImpl();
        }
        return positionRepository;
    }

    private static TransactionRepository getTransactionRepository() {
        if (transactionRepository == null) {
            transactionRepository = new TransactionRepositoryImpl();
        }
        return transactionRepository;
    }

    public static InnerApplicationFacade getInnerApplicationFacade() {
        if (innerApplicationFacade == null) {
            innerApplicationFacade = new InnerApplicationFacade(BusinessFacadeFactory.createOuterBusinessFacade());
        }
        return innerApplicationFacade;
    }
}
