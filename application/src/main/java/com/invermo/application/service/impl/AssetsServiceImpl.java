package com.invermo.application.service.impl;

import com.invermo.application.facade.InnerApplicationFacade;
import com.invermo.application.service.AssetsService;
import com.invermo.business.domain.Asset;
import com.invermo.business.domain.AssetPrice;

import java.math.BigDecimal;
import java.util.List;

public class AssetsServiceImpl implements AssetsService {

    private final InnerApplicationFacade innerApplicationFacade;

    public AssetsServiceImpl(InnerApplicationFacade innerApplicationFacade) {
        this.innerApplicationFacade = innerApplicationFacade;
    }

    @Override
    public List<Asset> getAllAssets() {
        return innerApplicationFacade.getAllAssets();
    }

    @Override
    public Asset getAssetById(final Long assetId) {
        return innerApplicationFacade.getAssetById(assetId);
    }

    @Override
    public List<Asset> getAssetsBySearchParam(String searchValue) {
        return innerApplicationFacade.getAssetsBySearchParam(searchValue);
    }

    @Override
    public List<AssetPrice> getAssetWithPriceByAssetSymbol(String symbol) {
        return innerApplicationFacade.getAssetWithPriceByAssetSymbol(symbol);
    }

    @Override
    public List<AssetPrice> getLatestAssetsPrice() {
        return innerApplicationFacade.getLatestAssetsPrice();
    }

    @Override
    public BigDecimal getLatestAssetPrice(Long assetId) {
        return innerApplicationFacade.getLatestAssetPrice(assetId);
    }

    @Override
    public void deleteAssetById(long id) {
        innerApplicationFacade.deleteAssetById(id);
    }

    @Override
    public void saveAsset(final Asset asset) {
        innerApplicationFacade.saveAsset(asset);
    }

    @Override
    public void saveAssetPrice(final List<AssetPrice> assetPrices) {
        innerApplicationFacade.saveAssetPrice(assetPrices);
    }
}
