package com.invermo.application.facade;

import com.invermo.business.domain.Asset;
import com.invermo.business.domain.AssetPrice;
import com.invermo.business.domain.User;
import com.invermo.business.facade.OuterBusinessFacade;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class InnerApplicationFacade {

    private OuterBusinessFacade outerBusinessFacade;

    public Optional<User> login(final String login, final String password) {
        return outerBusinessFacade.login(login, password);
    }

    public List<Asset> getAllAssets() {
        return outerBusinessFacade.getAllAssets();
    }

    public Asset getAssetById(Long assetId) {
        return outerBusinessFacade.getAssetById(assetId);
    }

    public List<Asset> getAssetsBySearchParam(String searchValue) {
        return outerBusinessFacade.getAssetsBySearchParam(searchValue);
    }

    public List<AssetPrice> getAssetWithPriceByAssetSymbol(String symbol) {
        return outerBusinessFacade.getAssetWithPriceByAssetSymbol(symbol);
    }

    public List<AssetPrice> getLatestAssetsPrice() {
        return outerBusinessFacade.getLatestAssetsPrice();
    }

    public BigDecimal getLatestAssetPrice(Long assetId) {
        return outerBusinessFacade.getLatestAssetPrice(assetId);
    }

    public void deleteAssetById(long id) {
        outerBusinessFacade.deleteAssetById(id);
    }

    public void saveAsset(final Asset asset) {
        outerBusinessFacade.saveAsset(asset);
    }

    public void saveAssetPrice(final List<AssetPrice> assetPrices) {
        outerBusinessFacade.saveAssetPrice(assetPrices);
    }
}
