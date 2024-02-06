package com.invermo.service;

import com.invermo.persistance.entity.Asset;

import java.util.List;

public interface AssetsService {
    List<Asset> getAllAssets();

    List<Asset> getAssetsBySearchParam(String searchValue);

    void deleteAssetById(long id);

    void saveAsset(Asset asset);
}
