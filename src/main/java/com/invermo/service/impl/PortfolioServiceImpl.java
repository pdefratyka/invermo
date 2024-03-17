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
import com.invermo.service.transaction.calculator.NumberOfAssetCalculator;
import com.invermo.service.transaction.calculator.PositionGainCalculator;
import com.invermo.service.transaction.calculator.PositionValueCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PortfolioServiceImpl implements PortfolioService {

    private static final Logger logger = Logger.getLogger(PortfolioServiceImpl.class.getName());

    private final Map<String, BigDecimal> latestPrices = new HashMap<>();

    private final AssetsService assetsService;
    private final PositionService positionService;
    private final TransactionService transactionService;

    public PortfolioServiceImpl(AssetsService assetsService, PositionService positionService, TransactionService transactionService) {
        this.assetsService = assetsService;
        this.positionService = positionService;
        this.transactionService = transactionService;
    }

    @Override
    public List<SinglePortfolioAsset> getPortfolioAssets() {
        initializeLatestPrices();
        final List<PositionWithAsset> positionWithAssets = positionService.getPositionsWithAssetsForUser();
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
                    .positionType(positionWithAsset.getPositionType().name())
                    .number(numberOfAsset)
                    .price(price)
                    .value(value)
                    .gain(gain)
                    .percentageGain(percentageGain)
                    .build();
            singlePortfolioAssets.add(portfolioAsset);
            portfolioAllValue = portfolioAllValue.add(value);
        }
        if (portfolioAllValue.intValue()==BigDecimal.ZERO.intValue()) {
            logger.info("Portfolio all value equals zero");
        } else {
            for (SinglePortfolioAsset singlePortfolioAsset : singlePortfolioAssets) {
                singlePortfolioAsset.setPercentagePortfolioPart(singlePortfolioAsset.getValue().divide(portfolioAllValue, 4, RoundingMode.FLOOR)
                        .multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.FLOOR));
            }
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

    private BigDecimal getLatestPrice(String symbol) {
        return assetsService.getAssetWithPriceByAssetSymbol(symbol).stream()
                .sorted(Comparator.comparing(AssetPrice::getDateTime, Comparator.nullsFirst(Comparator.naturalOrder())).reversed())
                .map(AssetPrice::getPrice)
                .filter(Objects::nonNull)
                .findFirst().orElseGet(() -> {
                    logger.warning("Price not found for asset: " + symbol);
                    return BigDecimal.ZERO;
                });
    }

    private BigDecimal getValue(final PositionWithAsset positionWithAsset, final BigDecimal numberOfAsset) {
        final BigDecimal latestPrice = getLatestPrice(positionWithAsset.getAssetSymbol());
        final Currency assetCurrency = positionWithAsset.getCurrency();
        final BigDecimal latestCurrencyExchange = latestPrices.get(assetCurrency.name() + "/PLN");
        return PositionValueCalculator.getPositionValue(numberOfAsset, latestPrice, latestCurrencyExchange);
    }

    private void initializeLatestPrices() {
        final BigDecimal usdPln = getLatestPrice("USD/PLN");
        final BigDecimal plnPLn = getLatestPrice("PLN/PLN");
        latestPrices.put("USD/PLN", usdPln);
        latestPrices.put("PLN/PLN", plnPLn);
    }
}
