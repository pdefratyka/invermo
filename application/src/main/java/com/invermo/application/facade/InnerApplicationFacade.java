package com.invermo.application.facade;

import com.invermo.business.domain.Asset;
import com.invermo.business.domain.AssetPrice;
import com.invermo.business.domain.Position;
import com.invermo.business.domain.PositionGain;
import com.invermo.business.domain.PositionWithAsset;
import com.invermo.business.domain.SinglePortfolioAsset;
import com.invermo.business.domain.SingleTransactionRecord;
import com.invermo.business.domain.Transaction;
import com.invermo.business.domain.User;
import com.invermo.business.facade.OuterBusinessFacade;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
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

    public List<Asset> getAssetsBySearchParam(String searchValue) {
        return outerBusinessFacade.getAssetsBySearchParam(searchValue);
    }

    public List<AssetPrice> getAssetWithPriceByAssetSymbol(String symbol) {
        return outerBusinessFacade.getAssetWithPriceByAssetSymbol(symbol);
    }

    public List<AssetPrice> getLatestAssetsPrice() {
        return outerBusinessFacade.getLatestAssetsPrice();
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


    public void addNewPosition(final Position position, final Long userId) {
        outerBusinessFacade.addNewPosition(position, userId);
    }

    public List<PositionWithAsset> getPositionsWithAssetsForUser(final Long userId) {
        return outerBusinessFacade.getPositionsWithAssetsForUser(userId);
    }

    public void saveTransaction(final Transaction transaction) {
        outerBusinessFacade.saveTransaction(transaction);
    }

    public List<SingleTransactionRecord> getSingleTransactionsForPosition(Long positionId, Long userId) {
        return outerBusinessFacade.getSingleTransactionsForPosition(positionId, userId);
    }

    public PositionGain getPositionGain(Long positionId, Long userId) {
        return outerBusinessFacade.getPositionGain(positionId, userId);
    }

    public PositionGain getPositionsGainByUserId(Long userId) {
        return outerBusinessFacade.getPositionsGainByUserId(userId);
    }

    public List<SinglePortfolioAsset> getPortfolioAssets(Long userId) {
        return outerBusinessFacade.getPortfolioAssets(userId);
    }

    public Map<String, Long> updateAllAssetsPricesFromOneFile(String fileName) {
        return outerBusinessFacade.updateAllAssetsPricesFromOneFile(fileName);
    }
}
