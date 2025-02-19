package com.invermo.business.facade;

import com.invermo.business.domain.Asset;
import com.invermo.business.domain.AssetPrice;
import com.invermo.business.domain.Category;
import com.invermo.business.domain.Position;
import com.invermo.business.domain.PositionGain;
import com.invermo.business.domain.PositionWithAsset;
import com.invermo.business.domain.SinglePortfolioAsset;
import com.invermo.business.domain.SingleTransactionRecord;
import com.invermo.business.domain.Transaction;
import com.invermo.business.domain.User;
import com.invermo.business.service.AssetPriceService;
import com.invermo.business.service.AssetService;
import com.invermo.business.service.AuthenticationService;
import com.invermo.business.service.CategoryService;
import com.invermo.business.service.CompositionService;
import com.invermo.business.service.PortfolioService;
import com.invermo.business.service.PositionGainService;
import com.invermo.business.service.PositionService;
import com.invermo.business.service.TransactionDetailsCalculator;
import com.invermo.business.service.TransactionService;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class OuterBusinessFacade {

    private final AuthenticationService authenticationService;
    private final AssetService assetService;
    private final PositionService positionService;
    private final TransactionService transactionService;
    private final TransactionDetailsCalculator transactionDetailsCalculator;
    private final PositionGainService positionGainService;
    private final PortfolioService portfolioService;
    private final AssetPriceService assetPriceService;
    private final CategoryService categoryService;
    private final CompositionService compositionService;

    public Optional<User> login(final String login, final String password) {
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

    public List<Position> getAllPositionsForUser(final Long userId) {
        return positionService.getAllPositionsForUser(userId);
    }

    public Position getPositionById(final Long positionId, final Long userId) {
        return positionService.getPositionById(positionId, userId);
    }

    public void addNewPosition(final Position position, final Long userId) {
        positionService.addNewPosition(position, userId);
    }

    public List<PositionWithAsset> getPositionsWithAssetsForUser(final Long userId) {
        return positionService.getPositionsWithAssetsForUser(userId);
    }

    public void saveTransaction(final Transaction transaction) {
        transactionService.saveTransaction(transaction);
    }

    public List<Transaction> getAllTransactionsForPositions(List<Long> positionIds) {
        return transactionService.getAllTransactionsForPositions(positionIds);
    }

    public List<Transaction> getAllTransactionForPosition(Long positionId) {
        return transactionService.getAllTransactionsForPositions(List.of(positionId));
    }

    public List<SingleTransactionRecord> getSingleTransactionsForPosition(Long positionId, Long userId) {
        return transactionDetailsCalculator.getSingleTransactionsForPosition(positionId, userId);
    }

    public PositionGain getPositionGain(Long positionId, Long userId) {
        return positionGainService.getMonthlyPositionGain(positionId);
    }

    public PositionGain getPositionsGainByUserId(Long userId) {
        return positionGainService.getMonthlyPositionGainByUserId(userId);
    }

    public List<SinglePortfolioAsset> getPortfolioAssets(Long userId) {
        return portfolioService.getPortfolioAssets(userId);
    }

    public Map<String, Long> updateAllAssetsPricesFromOneFile() throws GeneralSecurityException, IOException {
        return assetPriceService.updateAllAssetsPricesFromOneFile();
    }

    public List<Category> getAllMainCategories() {
        return categoryService.getAllMainCategories();
    }

    public List<Category> getChildCategoriesByParentId(long parentId) {
        return categoryService.getChildCategoriesByParentId(parentId);
    }

    public List<Category> getCategoriesByAssetId(long assetId) {
        return categoryService.getCategoriesByAssetId(assetId);
    }

    public void saveAssetCategory(long assetId, long categoryId) {
        categoryService.saveAssetCategory(assetId, categoryId);
    }

    public Map<String, BigDecimal> getComposition(Long userId, long categoryId) {
        return compositionService.getComposition(userId, categoryId);
    }
}
