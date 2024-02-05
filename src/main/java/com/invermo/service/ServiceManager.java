package com.invermo.service;

import com.invermo.persistance.repository.AssetRepository;
import com.invermo.persistance.repository.UserRepository;
import com.invermo.persistance.repository.impl.assets.AssetRepositoryImpl;
import com.invermo.persistance.repository.impl.users.UserRepositoryImpl;
import com.invermo.service.impl.AssetsServiceImpl;
import com.invermo.service.impl.AuthenticationServiceImpl;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceManager {

    private static AuthenticationService authenticationService;
    private static UserRepository userRepository;
    private static AssetRepository assetRepository;
    private static AssetsService assetsService;

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
}
