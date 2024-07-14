package com.invermo.business.service.persistence;

import com.invermo.business.domain.Asset;
import com.invermo.business.domain.AssetPrice;
import com.invermo.business.facade.InnerBusinessFacade;

import java.math.BigDecimal;
import java.util.List;

public class AssetPersistenceService {
    private final InnerBusinessFacade innerBusinessFacade;

    public AssetPersistenceService(InnerBusinessFacade innerBusinessFacade) {
        this.innerBusinessFacade = innerBusinessFacade;
    }

    public List<Asset> findAllAssets() {
        return innerBusinessFacade.findAllAssets();
    }

    public List<Asset> getAssetsBySearchParam(String searchValue) {
        return innerBusinessFacade.getAssetsBySearchParam(searchValue);
    }

    public List<AssetPrice> getAssetWithPriceByAssetSymbol(String symbol) {
        return innerBusinessFacade.getAssetWithPriceByAssetSymbol(symbol);
    }

    public List<AssetPrice> getLatestAssetsPrice() {
        return innerBusinessFacade.getLatestAssetsPrice();
    }

    public BigDecimal getLatestAssetPrice(Long assetId) {
        return innerBusinessFacade.getLatestAssetPrice(assetId);
    }

    public void deleteAssetById(long id) {
        innerBusinessFacade.deleteAssetById(id);
    }

    public void saveAsset(final Asset asset) {
        innerBusinessFacade.saveAsset(asset);
    }

    public void saveAssetPrice(final List<AssetPrice> assetPrices) {
        innerBusinessFacade.saveAssetPrice(assetPrices);
    }
}
