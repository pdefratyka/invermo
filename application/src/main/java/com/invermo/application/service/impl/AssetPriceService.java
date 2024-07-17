package com.invermo.application.service.impl;

import com.invermo.application.facade.InnerApplicationFacade;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class AssetPriceService {

    private final InnerApplicationFacade innerApplicationFacade;

    public Map<String, Long> updateAllAssetsPricesFromOneFile(final String fileName) {
        return innerApplicationFacade.updateAllAssetsPricesFromOneFile(fileName);
    }
}
