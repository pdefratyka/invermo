package com.invermo.service;

import com.invermo.persistance.entity.Asset;
import com.invermo.persistance.entity.AssetPrice;

import java.math.BigDecimal;
import java.util.List;

public interface AssetsService {
    List<Asset> getAllAssets();

    Asset getAssetById(Long assetId);

    List<Asset> getAssetsBySearchParam(String searchValue);

    List<AssetPrice> getAssetWithPriceByAssetSymbol(String symbol);

    List<AssetPrice> getLatestAssetsPrice();

    BigDecimal getLatestAssetPrice(Long assetId);

    void deleteAssetById(long id);

    void saveAsset(Asset asset);

    void saveAssetPrice(List<AssetPrice> assetPrices);
}
