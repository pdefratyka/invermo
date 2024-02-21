package com.invermo.service.impl;

import com.invermo.gui.portfolio.dto.SinglePortfolioAsset;
import com.invermo.persistance.entity.AssetPrice;
import com.invermo.persistance.entity.PositionWithAsset;
import com.invermo.persistance.entity.Transaction;
import com.invermo.persistance.enumeration.Currency;
import com.invermo.service.AssetsService;
import com.invermo.service.PortfolioService;
import com.invermo.service.PositionService;
import com.invermo.service.TransactionService;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
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
        final BigDecimal priceOfUsdPln = getLatestPrice("USD/PLN");
        for (PositionWithAsset positionWithAsset : positionWithAssets) {
            final BigDecimal numberOfAsset = calculateNumberOfAsset(transactionsForPosition.get(positionWithAsset.getPositionId()));
            final BigDecimal value = getValue(positionWithAsset, priceOfUsdPln, numberOfAsset);
            final BigDecimal gain = calculateGain(transactionsForPosition.get(positionWithAsset.getPositionId()), value);
            final SinglePortfolioAsset portfolioAsset = SinglePortfolioAsset.builder()
                    .name(positionWithAsset.getAssetName())
                    .assetType(positionWithAsset.getAssetType().getName())
                    .positionType(positionWithAsset.getPositionType().name())
                    .number(numberOfAsset)
                    .value(value)
                    .gain(gain)
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

    private BigDecimal getLatestPrice(String symbol) {
        final List<AssetPrice> prices = assetsService.getAssetWithPriceByAssetSymbol(symbol);
        return prices.stream()
                .sorted(Comparator.comparing(AssetPrice::getDateTime).reversed())
                .map(AssetPrice::getPrice)
                .findFirst().orElseThrow(() -> new RuntimeException("Asset with given symbol does not exists"));
    }

    private BigDecimal getValue(final PositionWithAsset positionWithAsset, final BigDecimal usdPln, final BigDecimal numberOfAsset) {
        final BigDecimal latestPrice = getLatestPrice(positionWithAsset.getAssetSymbol());
        if (positionWithAsset.getCurrency().equals(Currency.PLN)) {
            return latestPrice.multiply(numberOfAsset);
        } else if (positionWithAsset.getCurrency().equals(Currency.USD)) {
            return latestPrice.multiply(numberOfAsset).multiply(usdPln);
        }
        throw new RuntimeException("Unsupported currency");
    }

    private BigDecimal calculateGain(final List<Transaction> transactions, final BigDecimal value) {
        // ADD currency, add option for sell/buy
        final BigDecimal cost = transactions.stream()
                .map(t->t.getPrice().multiply(t.getNumberOfAsset()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return value.subtract(cost);
    }
}
