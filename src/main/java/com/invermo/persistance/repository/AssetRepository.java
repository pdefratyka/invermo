package com.invermo.persistance.repository;

import com.invermo.persistance.entity.Asset;

import java.util.List;

public interface AssetRepository {

    List<Asset> getAllAssets();

    void saveAsset(Asset asset);

    void deleteAssetById(long id);
}
