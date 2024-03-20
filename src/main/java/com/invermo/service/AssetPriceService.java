package com.invermo.service;

public interface AssetPriceService {
    void updateAllAssetsPrices();

    void updateAllAssetsPricesFromOneFile(String fileName);
}
