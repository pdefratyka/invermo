package com.invermo.business.service;

import com.invermo.business.domain.Asset;
import com.invermo.business.domain.AssetPrice;
import com.invermo.business.service.persistence.AssetPersistenceService;

import java.math.BigDecimal;
import java.util.List;

public class AssetService {

    private final AssetPersistenceService assetPersistenceService;

    public AssetService(AssetPersistenceService assetPersistenceService) {
        this.assetPersistenceService = assetPersistenceService;
    }

    public List<Asset> findAllAssets() {
        return assetPersistenceService.findAllAssets();
    }

    public Asset getAssetById(Long assetId) {
        final List<Asset> assets = findAllAssets();
        return assets.stream().filter(asset -> asset.assetId().equals(assetId))
                .findFirst().orElseThrow(() -> new RuntimeException("No asset found with given id"));
    }

    public List<Asset> getAssetsBySearchParam(String searchValue) {
        return assetPersistenceService.getAssetsBySearchParam(searchValue);
    }

    public List<AssetPrice> getAssetWithPriceByAssetSymbol(String symbol) {
        return assetPersistenceService.getAssetWithPriceByAssetSymbol(symbol);
    }

    public List<AssetPrice> getLatestAssetsPrice() {
        return assetPersistenceService.getLatestAssetsPrice();
    }

    public BigDecimal getLatestAssetPrice(Long assetId) {
        return assetPersistenceService.getLatestAssetPrice(assetId);
    }

    public void deleteAssetById(long id) {
        assetPersistenceService.deleteAssetById(id);
    }

    public void saveAsset(final Asset asset) {
        assetPersistenceService.saveAsset(asset);
    }

    public void saveAssetPrice(final List<AssetPrice> assetPrices) {
        assetPersistenceService.saveAssetPrice(assetPrices);
    }
}
