package com.invermo.application.service.impl;

import com.invermo.application.facade.InnerApplicationFacade;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

@AllArgsConstructor
public class AssetPriceService {

    private final InnerApplicationFacade innerApplicationFacade;

    public Map<String, Long> updateAllAssetsPricesFromOneFile() throws GeneralSecurityException, IOException {
        return innerApplicationFacade.updateAllAssetsPricesFromOneFile();
    }
}
