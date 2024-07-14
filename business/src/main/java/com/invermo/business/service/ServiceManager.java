package com.invermo.business.service;

import com.invermo.business.facade.InnerBusinessFacade;
import com.invermo.business.service.persistence.AssetPersistenceService;
import com.invermo.business.service.persistence.PositionPersistenceService;
import com.invermo.business.service.persistence.TransactionPersistenceService;
import com.invermo.business.service.persistence.UserPersistenceService;

public class ServiceManager {

    private static AuthenticationService authenticationService;
    private static AssetService assetService;
    private static UserPersistenceService userPersistenceService;
    private static AssetPersistenceService assetPersistenceService;
    private static PositionPersistenceService positionPersistenceService;
    private static PositionService positionService;
    private static TransactionPersistenceService transactionPersistenceService;
    private static TransactionService transactionService;
    private static TransactionDetailsCalculator transactionDetailsCalculator;
    private static PositionGainService positionGainService;

    public static UserPersistenceService getUserPersistenceService() {
        if (userPersistenceService == null) {
            userPersistenceService = new UserPersistenceService(InnerBusinessFacade.getInstance());
        }
        return userPersistenceService;
    }

    public static AuthenticationService getAuthenticationService() {
        if (authenticationService == null) {
            authenticationService = new AuthenticationService(getUserPersistenceService());
        }
        return authenticationService;
    }

    public static AssetPersistenceService getAssetPersistenceService() {
        if (assetPersistenceService == null) {
            assetPersistenceService = new AssetPersistenceService(InnerBusinessFacade.getInstance());
        }
        return assetPersistenceService;
    }

    public static AssetService getAssetService() {
        if (assetService == null) {
            assetService = new AssetService(getAssetPersistenceService());
        }
        return assetService;
    }

    public static PositionPersistenceService getPositionPersistenceService() {
        if (positionPersistenceService == null) {
            positionPersistenceService = new PositionPersistenceService(InnerBusinessFacade.getInstance());
        }
        return positionPersistenceService;
    }

    public static PositionService getPositionService() {
        if (positionService == null) {
            positionService = new PositionService(getPositionPersistenceService());
        }
        return positionService;
    }

    public static TransactionPersistenceService getTransactionPersistenceService() {
        if (transactionPersistenceService == null) {
            transactionPersistenceService = new TransactionPersistenceService(InnerBusinessFacade.getInstance());
        }
        return transactionPersistenceService;
    }

    public static TransactionService getTransactionService() {
        if (transactionService == null) {
            transactionService = new TransactionService(getTransactionPersistenceService());
        }
        return transactionService;
    }

    public static TransactionDetailsCalculator getTransactionDetailsCalculator() {
        if (transactionDetailsCalculator == null) {
            transactionDetailsCalculator = new TransactionDetailsCalculator(getTransactionService(), getAssetService(), getPositionService());
        }
        return transactionDetailsCalculator;
    }

    public static PositionGainService getPositionGainService() {
        if (positionGainService == null) {
            positionGainService = new PositionGainService(getTransactionService(), getPositionService(), getAssetService());
        }
        return positionGainService;
    }
}
