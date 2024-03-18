package com.invermo.service;

import com.invermo.persistance.repository.AssetRepository;
import com.invermo.persistance.repository.PortfolioRepository;
import com.invermo.persistance.repository.PositionRepository;
import com.invermo.persistance.repository.TransactionRepository;
import com.invermo.persistance.repository.UserRepository;
import com.invermo.persistance.repository.impl.assets.AssetRepositoryImpl;
import com.invermo.persistance.repository.impl.portfolio.PortfolioRepositoryImpl;
import com.invermo.persistance.repository.impl.portfolio.position.PositionRepositoryImpl;
import com.invermo.persistance.repository.impl.portfolio.transaction.TransactionRepositoryImpl;
import com.invermo.persistance.repository.impl.users.UserRepositoryImpl;
import com.invermo.service.impl.AssetsServiceImpl;
import com.invermo.service.impl.AuthenticationServiceImpl;
import com.invermo.service.impl.PortfolioServiceImpl;
import com.invermo.service.impl.PositionServiceImpl;
import com.invermo.service.impl.TransactionServiceImpl;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceManager {

    private static AuthenticationService authenticationService;
    private static UserRepository userRepository;
    private static AssetRepository assetRepository;
    private static PortfolioRepository portfolioRepository;
    private static AssetsService assetsService;
    private static PortfolioService portfolioService;
    private static PositionService positionService;
    private static PositionRepository positionRepository;
    private static TransactionRepository transactionRepository;
    private static TransactionService transactionService;

    public static AuthenticationService getAuthenticationService() {
        if (authenticationService == null) {
            authenticationService = new AuthenticationServiceImpl(getUserRepository());
        }
        return authenticationService;
    }

    public static AssetsService getAssetsService() {
        if (assetsService == null) {
            assetsService = new AssetsServiceImpl(getAssetRepository());
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

    private static UserRepository getUserRepository() {
        if (userRepository == null) {
            userRepository = new UserRepositoryImpl();
        }
        return userRepository;
    }

    private static AssetRepository getAssetRepository() {
        if (assetRepository == null) {
            assetRepository = new AssetRepositoryImpl();
        }
        return assetRepository;
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
}
