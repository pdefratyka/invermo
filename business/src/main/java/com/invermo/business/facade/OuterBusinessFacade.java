package com.invermo.business.facade;

import com.invermo.business.domain.Asset;
import com.invermo.business.domain.AssetPrice;
import com.invermo.business.domain.User;
import com.invermo.business.service.AssetService;
import com.invermo.business.service.AuthenticationService;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class OuterBusinessFacade {

    private final AuthenticationService authenticationService;
    private final AssetService assetService;

    public Optional<User> login(final String login, final String password) {
        System.out.println("test from business facade");
        return authenticationService.login(login, password);
    }

    public List<Asset> getAllAssets() {
        return assetService.findAllAssets();
    }

    public Asset getAssetById(Long assetId) {
        return assetService.getAssetById(assetId);
    }

    public List<Asset> getAssetsBySearchParam(String searchValue) {
        return assetService.getAssetsBySearchParam(searchValue);
    }

    public List<AssetPrice> getAssetWithPriceByAssetSymbol(String symbol) {
        return assetService.getAssetWithPriceByAssetSymbol(symbol);
    }

    public List<AssetPrice> getLatestAssetsPrice() {
        return assetService.getLatestAssetsPrice();
    }

    public BigDecimal getLatestAssetPrice(Long assetId) {
        return assetService.getLatestAssetPrice(assetId);
    }

    public void deleteAssetById(long id) {
        assetService.deleteAssetById(id);
    }

    public void saveAsset(final Asset asset) {
        assetService.saveAsset(asset);
    }

    public void saveAssetPrice(final List<AssetPrice> assetPrices) {
        assetService.saveAssetPrice(assetPrices);
    }
}
