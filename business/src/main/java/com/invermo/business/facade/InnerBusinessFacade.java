package com.invermo.business.facade;

import com.invermo.business.domain.Asset;
import com.invermo.business.domain.AssetPrice;
import com.invermo.business.domain.User;
import com.invermo.business.mapper.AssetMapper;
import com.invermo.business.mapper.AssetPriceMapper;
import com.invermo.business.mapper.UserMapper;
import com.invermo.persistence.facade.OuterPersistenceFacade;
import com.invermo.persistence.facade.PersistenceFacadeFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InnerBusinessFacade {

    private static final InnerBusinessFacade innerBusinessFacade = new InnerBusinessFacade();
    private final OuterPersistenceFacade outerPersistenceFacade;

    private InnerBusinessFacade() {
        outerPersistenceFacade = PersistenceFacadeFactory.createOuterPersistenceFacade();
    }

    public Optional<User> findUserByLoginAndPassword(final String login, final String password) {
        return outerPersistenceFacade.findUserByLoginAndPassword(login, password)
                .map(UserMapper::mapUserEntityToUser);
    }

    public List<Asset> findAllAssets() {
        return outerPersistenceFacade.findAllAssets().stream()
                .map(AssetMapper::mapAssetEntityToAsset)
                .collect(Collectors.toList());
    }

    public List<Asset> getAssetsBySearchParam(final String searchValue) {
        return outerPersistenceFacade.searchAssets(searchValue).stream()
                .map(AssetMapper::mapAssetEntityToAsset)
                .collect(Collectors.toList());
    }

    public List<AssetPrice> getAssetWithPriceByAssetSymbol(final String symbol) {
        return outerPersistenceFacade.getAssetWithPriceByAssetSymbol(symbol).stream()
                .map(AssetPriceMapper::mapAssetPriceEntityToAssetPrice)
                .collect(Collectors.toList());
    }

    public List<AssetPrice> getLatestAssetsPrice() {
        return outerPersistenceFacade.getLatestAssetsPrices().stream()
                .map(AssetPriceMapper::mapAssetPriceEntityToAssetPrice)
                .collect(Collectors.toList());
    }

    public BigDecimal getLatestAssetPrice(final Long assetId) {
        return outerPersistenceFacade.getLatestAssetPrice(assetId);
    }

    public void deleteAssetById(long id) {
        outerPersistenceFacade.deleteAssetById(id);
    }

    public void saveAsset(final Asset asset) {
        outerPersistenceFacade.saveAsset(AssetMapper.mapAssetToAssentEntity(asset));
    }

    public void saveAssetPrice(final List<AssetPrice> assetPrices) {
        outerPersistenceFacade.saveAssetPrices(AssetPriceMapper.mapToAssetPriceEntities(assetPrices));
    }

    public static InnerBusinessFacade getInstance() {
        return innerBusinessFacade;
    }


}
