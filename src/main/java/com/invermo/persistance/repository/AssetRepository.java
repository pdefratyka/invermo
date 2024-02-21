package com.invermo.persistance.repository;

import com.invermo.persistance.entity.Asset;
import com.invermo.persistance.entity.AssetPrice;
import com.invermo.persistance.entity.AssetWithPrice;

import java.util.List;

public interface AssetRepository {

    List<Asset> getAllAssets();

    List<Asset> searchAssets(String searchValue);

    void saveAsset(Asset asset);

    void deleteAssetById(long id);

    List<AssetPrice> getAssetWithPriceByAssetSymbol(String symbol);
}
