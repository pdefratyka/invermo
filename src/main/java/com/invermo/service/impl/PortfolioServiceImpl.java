package com.invermo.service.impl;

import com.invermo.gui.portfolio.dto.SinglePortfolioAsset;
import com.invermo.persistance.entity.PositionWithAsset;
import com.invermo.persistance.entity.Transaction;
import com.invermo.service.AssetsService;
import com.invermo.service.PortfolioService;
import com.invermo.service.PositionService;
import com.invermo.service.TransactionService;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final AssetsService assetsService;
    private final PositionService positionService;
    private final TransactionService transactionService;

    @Override
    public List<SinglePortfolioAsset> getPortfolioAssets() {
        final List<PositionWithAsset> positionWithAssets = positionService.getPositionsWithAssetsForUser();
        final List<SinglePortfolioAsset> singlePortfolioAssets = new ArrayList<>();
        final List<Transaction> transactions = transactionService.getAllTransactionsForPositions(positionWithAssets.stream().map(PositionWithAsset::getPositionId).toList());
        final Map<Long, List<Transaction>> transactionsForPosition = convertTransactionsToMap(transactions, positionWithAssets);
        for (PositionWithAsset positionWithAsset : positionWithAssets) {
            final SinglePortfolioAsset portfolioAsset = SinglePortfolioAsset.builder()
                    .name(positionWithAsset.getAssetName())
                    .assetType(positionWithAsset.getAssetType().getName())
                    .positionType(positionWithAsset.getPositionType().name())
                    .number(calculateNumberOfAsset(transactionsForPosition.get(positionWithAsset.getPositionId())))
                    .value(BigDecimal.ZERO)
                    .gain(BigDecimal.ZERO)
                    .percentageGain(BigDecimal.ZERO)
                    .percentagePortfolioPart(BigDecimal.ZERO)
                    .build();
            singlePortfolioAssets.add(portfolioAsset);
        }
        return singlePortfolioAssets;
    }

    private Map<Long, List<Transaction>> convertTransactionsToMap(final List<Transaction> transactions, final List<PositionWithAsset> positionWithAssets) {
        final HashMap<Long, List<Transaction>> transactionsForPosition = new HashMap<>();
        final Set<Long> positionsIds = positionWithAssets.stream()
                .map(PositionWithAsset::getPositionId)
                .collect(Collectors.toSet());
        for (Long positionId : positionsIds) {
            final List<Transaction> tempTransactions = transactions.stream()
                    .filter(t -> t.getPositionId().equals(positionId))
                    .toList();
            transactionsForPosition.put(positionId, tempTransactions);
        }
        return transactionsForPosition;
    }

    private BigDecimal calculateNumberOfAsset(final List<Transaction> transactions) {
        return transactions.stream().map(Transaction::getNumberOfAsset)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
