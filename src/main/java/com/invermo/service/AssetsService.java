package com.invermo.service;

import com.invermo.persistance.entity.Asset;
import com.invermo.persistance.entity.AssetPrice;
import com.invermo.persistance.entity.AssetWithPrice;

import java.util.List;

public interface AssetsService {
    List<Asset> getAllAssets();

    List<Asset> getAssetsBySearchParam(String searchValue);

    List<AssetPrice> getAssetWithPriceByAssetSymbol(String symbol);

    void deleteAssetById(long id);

    void saveAsset(Asset asset);
}
