package com.invermo.service.impl;

import com.invermo.service.AssetPriceService;
import com.invermo.service.AssetsService;
import com.invermo.service.ServiceManager;
import org.junit.jupiter.api.Test;

public class AssetPriceServiceImplTest {

//    @Test
    void shouldListAllFilesInDirectory() {
        AssetsService assetsService = ServiceManager.getAssetsService();
        AssetPriceService assetPriceService = new AssetPriceServiceImpl(assetsService);
        assetPriceService.updateAllAssetsPrices();
    }
}
