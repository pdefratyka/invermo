package com.invermo.service;

import java.util.Map;

public interface AssetPriceService {
    void updateAllAssetsPrices();

    Map<String, Long> updateAllAssetsPricesFromOneFile(String fileName);
}
