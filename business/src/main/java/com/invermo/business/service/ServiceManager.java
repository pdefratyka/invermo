package com.invermo.business.service;

import com.invermo.business.facade.InnerBusinessFacade;
import com.invermo.business.service.persistence.AssetPersistenceService;
import com.invermo.business.service.persistence.UserPersistenceService;

public class ServiceManager {

    private static AuthenticationService authenticationService;
    private static AssetService assetService;
    private static UserPersistenceService userPersistenceService;
    private static AssetPersistenceService assetPersistenceService;

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
}
