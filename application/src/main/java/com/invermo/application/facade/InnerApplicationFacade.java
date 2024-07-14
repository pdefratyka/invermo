package com.invermo.application.facade;

import com.invermo.business.domain.Asset;
import com.invermo.business.domain.AssetPrice;
import com.invermo.business.domain.Position;
import com.invermo.business.domain.PositionGain;
import com.invermo.business.domain.PositionWithAsset;
import com.invermo.business.domain.SingleTransactionRecord;
import com.invermo.business.domain.Transaction;
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

    public List<Position> getAllPositionsForUser(final Long userId) {
        return outerBusinessFacade.getAllPositionsForUser(userId);
    }

    public Position getPositionById(final Long positionId, final Long userId) {
        return outerBusinessFacade.getPositionById(positionId, userId);
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

    public List<Transaction> getAllTransactionsForPositions(List<Long> positionIds) {
        return outerBusinessFacade.getAllTransactionsForPositions(positionIds);
    }

    public List<Transaction> getAllTransactionForPosition(Long positionId) {
        return outerBusinessFacade.getAllTransactionsForPositions(List.of(positionId));
    }

    public List<SingleTransactionRecord> getSingleTransactionsForPosition(Long positionId, Long userId) {
        return outerBusinessFacade.getSingleTransactionsForPosition(positionId, userId);
    }

    public PositionGain getPositionGain(Long positionId, Long userId) {
        return outerBusinessFacade.getPositionGain(positionId, userId);
    }
}
