package com.invermo.application.service.impl;

import com.invermo.application.facade.InnerApplicationFacade;
import com.invermo.business.domain.Asset;

import java.util.List;

public class AssetService {

    private final InnerApplicationFacade innerApplicationFacade;

    public AssetService(InnerApplicationFacade innerApplicationFacade) {
        this.innerApplicationFacade = innerApplicationFacade;
    }

    public List<Asset> getAllAssets() {
        return innerApplicationFacade.getAllAssets();
    }

    public List<Asset> getAssetsBySearchParam(String searchValue) {
        return innerApplicationFacade.getAssetsBySearchParam(searchValue);
    }

    public void deleteAssetById(long id) {
        innerApplicationFacade.deleteAssetById(id);
    }

    public void saveAsset(final Asset asset) {
        innerApplicationFacade.saveAsset(asset);
    }
}
