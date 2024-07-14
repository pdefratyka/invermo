package com.invermo.application.service.impl;

import com.invermo.application.facade.InnerApplicationFacade;
import com.invermo.business.domain.Asset;
import com.invermo.business.domain.AssetPrice;

import java.math.BigDecimal;
import java.util.List;

public class AssetService {

    private final InnerApplicationFacade innerApplicationFacade;

    public AssetService(InnerApplicationFacade innerApplicationFacade) {
        this.innerApplicationFacade = innerApplicationFacade;
    }

    public List<Asset> getAllAssets() {
        return innerApplicationFacade.getAllAssets();
    }

    public Asset getAssetById(final Long assetId) {
        return innerApplicationFacade.getAssetById(assetId);
    }

    public List<Asset> getAssetsBySearchParam(String searchValue) {
        return innerApplicationFacade.getAssetsBySearchParam(searchValue);
    }

    public List<AssetPrice> getAssetWithPriceByAssetSymbol(String symbol) {
        return innerApplicationFacade.getAssetWithPriceByAssetSymbol(symbol);
    }

    public List<AssetPrice> getLatestAssetsPrice() {
        return innerApplicationFacade.getLatestAssetsPrice();
    }

    public BigDecimal getLatestAssetPrice(Long assetId) {
        return innerApplicationFacade.getLatestAssetPrice(assetId);
    }

    public void deleteAssetById(long id) {
        innerApplicationFacade.deleteAssetById(id);
    }

    public void saveAsset(final Asset asset) {
        innerApplicationFacade.saveAsset(asset);
    }

    public void saveAssetPrice(final List<AssetPrice> assetPrices) {
        innerApplicationFacade.saveAssetPrice(assetPrices);
    }
}
