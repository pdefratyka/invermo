package com.invermo.service;

import com.invermo.persistance.entity.Asset;

import java.util.List;

public interface AssetsService {
    List<Asset> getAllAssets();

    void deleteAssetById(long id);

    void saveAsset(Asset asset);
}
