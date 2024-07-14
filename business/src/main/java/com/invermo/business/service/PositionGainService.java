package com.invermo.business.service;

import com.invermo.business.domain.AssetPrice;
import com.invermo.business.domain.Position;
import com.invermo.business.domain.PositionGain;
import com.invermo.business.domain.PositionWithAsset;
import com.invermo.business.domain.Transaction;
import com.invermo.business.enumeration.TransactionType;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@AllArgsConstructor
public class PositionGainService {
    private static final YearMonth DEFAULT_YEAR_MONTH_START = YearMonth.of(2021, 1);
    private static final String DEFAULT_CURRENCY = "PLN";
    private final TransactionService transactionService;
    private final PositionService positionService;
    private final AssetService assetService;

    public PositionGain getMonthlyPositionGainByUserId(final Long userId) {
        final List<Position> positions = positionService.getAllPositionsForUser(userId);
        final List<PositionGain> positionGains = new ArrayList<>();
        for (Long positionId : positions.stream().map(Position::getId).toList()) {
            PositionGain positionGain = getMonthlyPositionGain(positionId);
            positionGains.add(positionGain);
        }
        return sumPositionGains(positionGains);
    }

    private PositionGain sumPositionGains(final List<PositionGain> positionGains) {
        // Monthly Gain
        final Map<YearMonth, BigDecimal> monthlyGain = getMonthlyPositionGainSummary(positionGains);
        final PositionGain positionGain = new PositionGain();
        positionGain.setGainByMonth(monthlyGain);
        setCumulativeGain(positionGain);
        return positionGain;
    }

    private Map<YearMonth, BigDecimal> getMonthlyPositionGainSummary(final List<PositionGain> positionGains) {
        final List<Map<YearMonth, BigDecimal>> listOfMonthlyGains = positionGains.stream().map(PositionGain::getGainByMonth).toList();
        final Map<YearMonth, BigDecimal> result = new TreeMap<>();
        YearMonth yearMonth = YearMonth.of(2022, 5);
        while (!yearMonth.isAfter(YearMonth.now())) {
            BigDecimal value = BigDecimal.ZERO;
            for (Map<YearMonth, BigDecimal> c : listOfMonthlyGains) {
                BigDecimal temp = c.get(yearMonth);
                if (temp != null) {
                    value = value.add(temp);
                }
            }
            result.put(yearMonth, value);
            yearMonth = yearMonth.plusMonths(1);
        }

        return result;
    }

    public PositionGain getMonthlyPositionGain(final Long positionId) {
        final List<Transaction> transactions = transactionService.getAllTransactionForPosition(positionId);
        final PositionWithAsset positionWithAsset = positionService.getPositionWithAssetByPositionId(positionId);
        final List<AssetPrice> assetPrices = assetService.getAssetWithPriceByAssetSymbol(positionWithAsset.getAssetSymbol());
        final List<AssetPrice> currencyPrices = assetService
                .getAssetWithPriceByAssetSymbol(positionWithAsset.getCurrency().name() + "/" + DEFAULT_CURRENCY);
        PositionGain positionGain = new PositionGain();
        positionGain.setGainByMonth(new TreeMap<>());
        positionGain.setPercentageGainByMonth(new TreeMap<>());
        Map<YearMonth, BigDecimal> positionGainByMonth = new TreeMap<>();
        YearMonth yearMonth = getStartYearMonth(transactions);
        while (!yearMonth.isAfter(YearMonth.now())) {
            positionGainByMonth.put(yearMonth, setPositionGainForMonth(transactions, assetPrices, currencyPrices, yearMonth, positionGain));
            yearMonth = yearMonth.plusMonths(1);
        }
        //positionGain.setGainByMonth(positionGainByMonth);
        setCumulativeGain(positionGain);
        return positionGain;
    }

    private YearMonth getStartYearMonth(final List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            return DEFAULT_YEAR_MONTH_START;
        }
        return YearMonth.of(transactions.get(0).getDateTime().getYear(), transactions.get(0).getDateTime().getMonth()).minusMonths(1);
    }

    private void setCumulativeGain(PositionGain positionGain) {
        if (positionGain.getGainByMonth().isEmpty()) {
            return;
        }
        final Map<YearMonth, BigDecimal> gainByMonth = positionGain.getGainByMonth();
        final Map<YearMonth, BigDecimal> cumulativeGain = new TreeMap<>();

        final List<YearMonth> yearMonths = new ArrayList<>();
        YearMonth yearMonth = positionGain.getGainByMonth().keySet().stream().sorted().toList().get(0);
        while (!yearMonth.isAfter(YearMonth.now())) {
            yearMonths.add(yearMonth);
            yearMonth = yearMonth.plusMonths(1);
        }

        for (YearMonth tempYearMonth : yearMonths) {
            BigDecimal gain = gainByMonth.entrySet().stream()
                    .filter(entry -> !entry.getKey().isAfter(tempYearMonth))
                    .map(Map.Entry::getValue)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            cumulativeGain.put(tempYearMonth, gain);
        }
        positionGain.setCumulativeGainByMonth(cumulativeGain);
    }

    private BigDecimal setPositionGainForMonth(final List<Transaction> transactions, final List<AssetPrice> assetPrices,
                                               final List<AssetPrice> currencyPrices, final YearMonth yearMonth,
                                               final PositionGain positionGain) {
        final BigDecimal numberOfAssetsAtStart = getNumberOfAssetsBeforeDate(transactions, yearMonth);
        final BigDecimal numberOfAssetsAtEnd = getNumberOfAssetsBeforeDate(transactions, yearMonth.plusMonths(1));
        final BigDecimal assetPriceAtStart = getAssetPriceClosesToDate(assetPrices, yearMonth);
        final BigDecimal currencyPriceAtStart = getAssetPriceClosesToDate(currencyPrices, yearMonth);
        final BigDecimal assetPriceAtEnd = getAssetPriceClosesToDate(assetPrices, yearMonth.plusMonths(1));
        final BigDecimal currencyPriceAtEnd = getAssetPriceClosesToDate(currencyPrices, yearMonth.plusMonths(1));

        final BigDecimal valueOfBuyTransactions = getValueOfBuyTransactionsInMonth(transactions, yearMonth);
        final BigDecimal valueOfSellTransactions = getValueOfSellTransactionsInMonth(transactions, yearMonth);
        // startPosition - how many assets did I start with

        final BigDecimal startValue = numberOfAssetsAtStart.multiply(assetPriceAtStart).multiply(currencyPriceAtStart)
                .add(valueOfBuyTransactions);
        final BigDecimal endValue = numberOfAssetsAtEnd.multiply(assetPriceAtEnd).multiply(currencyPriceAtEnd).add(valueOfSellTransactions);

        final BigDecimal gain = endValue.subtract(startValue);
        BigDecimal percentageGain = BigDecimal.ZERO;
        if (startValue.compareTo(BigDecimal.ZERO) > 0) {
            percentageGain = gain.divide(startValue, 6, RoundingMode.FLOOR).multiply(BigDecimal.valueOf(100));
        }
        positionGain.getGainByMonth().put(yearMonth, gain);
        positionGain.getPercentageGainByMonth().put(yearMonth, percentageGain);
        return endValue.subtract(startValue);
    }

    private BigDecimal getValueOfBuyTransactionsInMonth(final List<Transaction> transactions, final YearMonth yearMonth) {
        return transactions.stream()
                .filter(t -> TransactionType.BUY.equals(t.getTransactionType()))
                .filter(t -> t.getDateTime().isAfter(yearMonth.atDay(1).atStartOfDay())
                        && t.getDateTime().isBefore(yearMonth.atEndOfMonth().atTime(23, 59, 59)))
                .map(t -> t.getPrice().multiply(t.getNumberOfAsset()).multiply(t.getCurrencyExchangeRate()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getValueOfSellTransactionsInMonth(final List<Transaction> transactions, final YearMonth yearMonth) {
        return transactions.stream()
                .filter(t -> TransactionType.SELL.equals(t.getTransactionType()))
                .filter(t -> t.getDateTime().isAfter(yearMonth.atDay(1).atStartOfDay())
                        && t.getDateTime().isBefore(yearMonth.atEndOfMonth().atTime(23, 59, 59)))
                .map(t -> t.getPrice().multiply(t.getNumberOfAsset()).multiply(t.getCurrencyExchangeRate()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getAssetPriceClosesToDate(List<AssetPrice> assetPrices, YearMonth yearMonth) {
        return assetPrices.stream()
                .filter(assetPrice -> assetPrice.getDateTime().isBefore(getStartOfMonth(yearMonth)))
                .sorted()
                .findFirst()
                .map(AssetPrice::getPrice)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal getNumberOfAssetsBeforeDate(final List<Transaction> transactions, final YearMonth yearMonth) {
        final LocalDateTime date = getStartOfMonth(yearMonth);
        return NumberOfAssetCalculator.getNumberOfAssetBeforeDate(transactions, date);
    }

    private LocalDateTime getStartOfMonth(final YearMonth yearMonth) {
        return yearMonth.atDay(1).atStartOfDay();
    }
}
