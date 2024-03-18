package com.invermo.service.impl;

import com.invermo.persistance.entity.Asset;
import com.invermo.persistance.entity.AssetPrice;
import com.invermo.persistance.repository.AssetRepository;
import com.invermo.service.AssetsService;

import java.util.List;

public class AssetsServiceImpl implements AssetsService {

    private final AssetRepository assetRepository;

    public AssetsServiceImpl(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @Override
    public List<Asset> getAllAssets() {
        return assetRepository.getAllAssets();
    }

    @Override
    public List<Asset> getAssetsBySearchParam(String searchValue) {
        return assetRepository.searchAssets(searchValue);
    }

    @Override
    public List<AssetPrice> getAssetWithPriceByAssetSymbol(String symbol) {
        return assetRepository.getAssetWithPriceByAssetSymbol(symbol);
    }

    @Override
    public List<AssetPrice> getLatestAssetsPrice() {
        return assetRepository.getLatestAssetsPrices();
    }

    @Override
    public void deleteAssetById(long id) {
        assetRepository.deleteAssetById(id);
    }

    @Override
    public void saveAsset(final Asset asset) {
        assetRepository.saveAsset(asset);
    }

    @Override
    public void saveAssetPrice(final List<AssetPrice> assetPrices) {
        assetRepository.saveAssetPrices(assetPrices);
    }
}
