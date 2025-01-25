package com.invermo.business.service;

import com.invermo.business.domain.AssetPrice;
import com.invermo.business.domain.PositionWithAsset;
import com.invermo.business.domain.SinglePortfolioAsset;
import com.invermo.business.domain.Transaction;
import com.invermo.business.enumeration.Currency;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class PortfolioService {

    private final Map<String, BigDecimal> latestPrices = new ConcurrentHashMap<>();

    private final AssetService assetsService;
    private final PositionService positionService;
    private final TransactionService transactionService;

    public PortfolioService(AssetService assetsService, PositionService positionService, TransactionService transactionService) {
        this.assetsService = assetsService;
        this.positionService = positionService;
        this.transactionService = transactionService;
    }

    public List<SinglePortfolioAsset> getPortfolioAssets(Long userId) {
        final List<PositionWithAsset> positionWithAssets = positionService.getPositionsWithAssetsForUser(userId);
        initializeLatestPrices(positionWithAssets);
        final List<SinglePortfolioAsset> singlePortfolioAssets = new ArrayList<>();
        final List<Transaction> transactions = transactionService.getAllTransactionsForPositions(positionWithAssets.stream().map(PositionWithAsset::getPositionId).toList());
        final Map<Long, List<Transaction>> transactionsForPosition = convertTransactionsToMap(transactions, positionWithAssets);
        BigDecimal portfolioAllValue = BigDecimal.ZERO;

        for (PositionWithAsset positionWithAsset : positionWithAssets) {
            final List<Transaction> transactionsPerAsset = transactionsForPosition.get(positionWithAsset.getPositionId());
            final BigDecimal numberOfAsset = NumberOfAssetCalculator.getNumberOfAsset(transactionsPerAsset);
            final BigDecimal value = getValue(positionWithAsset, numberOfAsset);
            final BigDecimal gain = PositionGainCalculator.getPositionGain(transactionsForPosition.get(positionWithAsset.getPositionId()), value, numberOfAsset);
            final BigDecimal price = PositionGainCalculator.getCost(transactionsForPosition.get(positionWithAsset.getPositionId()), numberOfAsset);
            final BigDecimal percentageGain = PositionGainCalculator.getPercentageGain(price, gain);
            final SinglePortfolioAsset portfolioAsset = SinglePortfolioAsset.builder()
                    .name(positionWithAsset.getAssetName())
                    .assetType(positionWithAsset.getAssetType().getName())
                    .assetId(positionWithAsset.getAssetId())
                    .positionType(positionWithAsset.getPositionType().name())
                    .positionId(positionWithAsset.getPositionId())
                    .number(numberOfAsset)
                    .price(price)
                    .value(value)
                    .gain(gain)
                    .percentageGain(percentageGain)
                    .build();
            singlePortfolioAssets.add(portfolioAsset);
            portfolioAllValue = portfolioAllValue.add(value);
        }
        if (portfolioAllValue.intValue() == BigDecimal.ZERO.intValue()) {
            log.info("Portfolio all value equals zero");
        } else {
            for (SinglePortfolioAsset singlePortfolioAsset : singlePortfolioAssets) {
                singlePortfolioAsset.setPercentagePortfolioPart(singlePortfolioAsset.getValue().divide(portfolioAllValue, 6, RoundingMode.FLOOR)
                        .multiply(BigDecimal.valueOf(100)));
            }
        }
        singlePortfolioAssets.sort(Comparator.comparing(SinglePortfolioAsset::getPercentagePortfolioPart).reversed());
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

    private BigDecimal getLatestPrice(String symbol) {
        return assetsService.getAssetWithPriceByAssetSymbol(symbol).stream()
                .sorted(Comparator.comparing(AssetPrice::getDateTime, Comparator.nullsFirst(Comparator.naturalOrder())).reversed())
                .map(AssetPrice::getPrice)
                .filter(Objects::nonNull)
                .findFirst().orElseGet(() -> {
                    log.warn("Price not found for asset: " + symbol);
                    return BigDecimal.ZERO;
                });
    }

    private BigDecimal getValue(final PositionWithAsset positionWithAsset, final BigDecimal numberOfAsset) {
        final BigDecimal latestPrice = latestPrices.get(positionWithAsset.getAssetSymbol());
        final Currency assetCurrency = positionWithAsset.getCurrency();
        final BigDecimal latestCurrencyExchange = latestPrices.get(assetCurrency.name() + "/PLN");
        return PositionValueCalculator.getPositionValue(numberOfAsset, latestPrice, latestCurrencyExchange);
    }

    private void initializeLatestPrices(final List<PositionWithAsset> positionWithAssets) {
        final Set<String> assetsSymbols = positionWithAssets.stream()
                .map(PositionWithAsset::getAssetSymbol)
                .collect(Collectors.toSet());
        final Set<String> currenciesSymbols = Set.of("USD/PLN", "PLN/PLN", "EUR/PLN", "GBP/PLN");

        final List<CompletableFuture<Void>> futures = Stream.concat(assetsSymbols.stream(), currenciesSymbols.stream())
                .map(symbol ->
                        CompletableFuture
                                .supplyAsync(() -> getLatestPrice(symbol))
                                .thenAccept(price -> latestPrices.put(symbol, price))
                )
                .toList();

        final CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        allOf.join();
    }
}
