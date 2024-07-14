package com.invermo.persistence.facade;

import com.invermo.persistence.entity.AssetEntity;
import com.invermo.persistence.entity.AssetPriceEntity;
import com.invermo.persistence.entity.PositionEntity;
import com.invermo.persistence.entity.PositionWithAssetEntity;
import com.invermo.persistence.entity.TransactionEntity;
import com.invermo.persistence.entity.UserEntity;
import com.invermo.persistence.repository.assets.AssetRepository;
import com.invermo.persistence.repository.positions.PositionRepository;
import com.invermo.persistence.repository.transactions.TransactionRepository;
import com.invermo.persistence.repository.users.UserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class OuterPersistenceFacade {

    private static final OuterPersistenceFacade outerPersistenceFacade = new OuterPersistenceFacade();

    private final UserRepository userRepository;
    private final AssetRepository assetRepository;
    private final PositionRepository positionRepository;
    private final TransactionRepository transactionRepository;

    private OuterPersistenceFacade() {
        userRepository = new UserRepository();
        assetRepository = new AssetRepository();
        positionRepository = new PositionRepository();
        transactionRepository = new TransactionRepository();
    }

    public Optional<UserEntity> findUserByLoginAndPassword(final String login, final String password) {
        return userRepository.findUserByLoginAndPassword(login, password);
    }

    public List<AssetEntity> findAllAssets() {
        return assetRepository.findAllAssets();
    }

    public List<AssetEntity> searchAssets(final String searchValue) {
        return assetRepository.searchAssets(searchValue);
    }

    public void saveAsset(final AssetEntity asset) {
        assetRepository.saveAsset(asset);
    }

    public void deleteAssetById(final long id) {
        assetRepository.deleteAssetById(id);
    }

    public List<AssetPriceEntity> getAssetWithPriceByAssetSymbol(final String symbol) {
        return assetRepository.getAssetWithPriceByAssetSymbol(symbol);
    }

    public List<AssetPriceEntity> getLatestAssetsPrices() {
        return assetRepository.getLatestAssetsPrices();
    }

    public BigDecimal getLatestAssetPrice(final Long assetId) {
        return assetRepository.getLatestAssetPrice(assetId);
    }

    public void saveAssetPrices(final List<AssetPriceEntity> assetPrices) {
        assetRepository.saveAssetPrices(assetPrices);
    }

    public void savePosition(final PositionEntity position) {
        positionRepository.savePosition(position);
    }

    public List<PositionEntity> getAllPositionsByUserId(Long userId) {
        return positionRepository.getAllPositionsByUserId(userId);
    }

    public PositionWithAssetEntity getPositionWithAssetByPositionId(final Long positionId) {
        return positionRepository.getPositionWithAssetByPositionId(positionId);
    }

    public List<PositionWithAssetEntity> getPositionsWithAssetsForUser(Long userId) {
        return positionRepository.getPositionsWithAssetsForUser(userId);
    }

    public void saveTransaction(TransactionEntity transaction) {
        transactionRepository.saveTransaction(transaction);
    }

    public List<TransactionEntity> getAllTransactionsForPositions(List<Long> positionsIds) {
        return transactionRepository.getAllTransactionsForPositions(positionsIds);
    }

    public static OuterPersistenceFacade getInstance() {
        return outerPersistenceFacade;
    }
}
