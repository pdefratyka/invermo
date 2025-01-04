package com.invermo.business.facade;

import com.invermo.business.domain.Asset;
import com.invermo.business.domain.AssetPrice;
import com.invermo.business.domain.Category;
import com.invermo.business.domain.Position;
import com.invermo.business.domain.PositionWithAsset;
import com.invermo.business.domain.Transaction;
import com.invermo.business.domain.User;
import com.invermo.business.mapper.AssetMapper;
import com.invermo.business.mapper.AssetPriceMapper;
import com.invermo.business.mapper.CategoryMapper;
import com.invermo.business.mapper.PositionMapper;
import com.invermo.business.mapper.TransactionMapper;
import com.invermo.business.mapper.UserMapper;
import com.invermo.persistence.entity.CategoryAssetEntity;
import com.invermo.persistence.facade.OuterPersistenceFacade;
import com.invermo.persistence.facade.PersistenceFacadeFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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


    public void savePosition(final Position position) {
        outerPersistenceFacade.savePosition(PositionMapper.mapPositionToPositionEntity(position));
    }

    public List<Position> getAllPositionsByUserId(Long userId) {
        return PositionMapper.mapPositionEntitiesToPositions(outerPersistenceFacade.getAllPositionsByUserId(userId));
    }

    public PositionWithAsset getPositionWithAssetByPositionId(final Long positionId) {
        return PositionMapper.mapPositionWithAssetEntityToPositionWithAsset(outerPersistenceFacade.getPositionWithAssetByPositionId(positionId));
    }

    public List<PositionWithAsset> getPositionsWithAssetsForUser(Long userId) {
        return PositionMapper.mapPositionWithAssetEntitiesToPositionsWithAssets(outerPersistenceFacade.getPositionsWithAssetsForUser(userId));
    }

    public void saveTransaction(final Transaction transaction) {
        outerPersistenceFacade.saveTransaction(TransactionMapper.mapTransactionToTransactionEntity(transaction));
    }

    public List<Transaction> getAllTransactionsForPositions(List<Long> positionIds) {
        return TransactionMapper.mapPositionEntitiesToPositions(
                outerPersistenceFacade.getAllTransactionsForPositions(positionIds));
    }

    public List<Category> getAllMainCategories() {
        return CategoryMapper.mapToCategories(
                outerPersistenceFacade.getAllMainCategories());
    }

    public List<Category> getChildCategoriesByParentId(final long parentId) {
        return CategoryMapper.mapToCategories(
                outerPersistenceFacade.getChildCategoriesByParentId(parentId));
    }

    public List<Category> getCategoriesByAssetId(long assetId) {
        return CategoryMapper.mapToCategories(
                outerPersistenceFacade.getCategoriesByAssetId(assetId));
    }

    public void saveAssetCategory(long assetId, long categoryId) {
        outerPersistenceFacade.saveAssetCategory(assetId, categoryId);
    }

    public static InnerBusinessFacade getInstance() {
        return innerBusinessFacade;
    }


}
