package com.invermo.application.service;

import java.util.Map;

public interface AssetPriceService {
    void updateAllAssetsPrices();

    Map<String, Long> updateAllAssetsPricesFromOneFile(String fileName);
}
