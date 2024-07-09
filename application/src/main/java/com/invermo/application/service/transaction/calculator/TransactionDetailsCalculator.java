package com.invermo.application.service.transaction.calculator;

import com.invermo.application.gui.portfolio.dto.SingleTransactionRecord;
import com.invermo.application.service.impl.AssetService;
import com.invermo.application.service.impl.PositionService;
import com.invermo.application.service.impl.TransactionService;
import com.invermo.business.domain.Asset;
import com.invermo.business.domain.AssetPrice;
import com.invermo.business.domain.Transaction;
import com.invermo.business.enumeration.TransactionType;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
public class TransactionDetailsCalculator {

    private final TransactionService transactionService;
    private final AssetService assetsService;
    private final PositionService positionService;

    public List<SingleTransactionRecord> getSingleTransactionsForPosition(final Long positionId) {
        final List<Transaction> transactions = transactionService.getAllTransactionForPosition(positionId);
        final List<SingleTransactionRecord> transactionRecords = new ArrayList<>();
        for (Transaction transaction : transactions) {
            final SingleTransactionRecord singleTransactionRecord = mapToSingleTransactionRecord(transaction);
            transactionRecords.add(singleTransactionRecord);
        }
        enrichSingleTransactionRecordsOfRealizedGain(transactionRecords, transactions);
        enrichSingleTransactionRecordsOfNumberOfActive(transactionRecords);
        enrichSingleTransactionRecordsOfActiveGain(transactionRecords);
        enrichSingleTransactionRecordsOfAllGain(transactionRecords);
        enrichSingleTransactionRecordsOfPart(transactionRecords);
        return transactionRecords;
    }

    private SingleTransactionRecord mapToSingleTransactionRecord(final Transaction transaction) {
        return SingleTransactionRecord.of(transaction);
    }

    private void enrichSingleTransactionRecordsOfRealizedGain(final List<SingleTransactionRecord> transactionRecords, final List<Transaction> transactions) {
        final List<Transaction> sellTransactions = transactions.stream()
                .filter(t -> TransactionType.SELL.equals(t.getTransactionType()))
                .toList();
        final List<SingleTransactionRecord> buyTransactions = transactionRecords.stream()
                .filter(t -> TransactionType.BUY.equals(t.getTransaction().getTransactionType()))
                .toList();

        for (Transaction transaction : sellTransactions) {
            BigDecimal numberOfAssets = transaction.getNumberOfAsset();
            for (SingleTransactionRecord buyTransaction : buyTransactions) {
                final BigDecimal numberOfAssetsToFill = buyTransaction.getTransaction().getNumberOfAsset().subtract(buyTransaction.getNumberOfSold());
                if (numberOfAssets.compareTo(numberOfAssetsToFill) <= 0) {
                    final BigDecimal sellPrice = transaction.getPrice().multiply(numberOfAssets).multiply(transaction.getCurrencyExchangeRate());
                    final BigDecimal buyPrice = buyTransaction.getPricePerOne().multiply(numberOfAssets);
                    final BigDecimal gain = sellPrice.subtract(buyPrice);
                    buyTransaction.setNumberOfSold(buyTransaction.getNumberOfSold().add(numberOfAssets));
                    buyTransaction.setRealizedGain(buyTransaction.getRealizedGain().add(gain));
                    break;
                } else {
                    numberOfAssets = numberOfAssets.subtract(numberOfAssetsToFill);
                    final BigDecimal sellPrice = transaction.getPrice().multiply(numberOfAssetsToFill).multiply(transaction.getCurrencyExchangeRate());
                    final BigDecimal buyPrice = buyTransaction.getPricePerOne().multiply(numberOfAssetsToFill);
                    final BigDecimal gain = sellPrice.subtract(buyPrice);
                    buyTransaction.setNumberOfSold(buyTransaction.getNumberOfSold().add(numberOfAssetsToFill));
                    buyTransaction.setRealizedGain(buyTransaction.getRealizedGain().add(gain));
                }
            }
        }


    }

    private void enrichSingleTransactionRecordsOfNumberOfActive(final List<SingleTransactionRecord> transactionRecords) {
        transactionRecords.forEach(transaction -> transaction.setNumberOfActive(transaction.getTransaction().getNumberOfAsset().subtract(transaction.getNumberOfSold())));
    }

    private void enrichSingleTransactionRecordsOfActiveGain(final List<SingleTransactionRecord> transactionRecords) {
        final Long assetId = positionService.getPositionById(transactionRecords.get(0).getTransaction().getPositionId()).getAssetId();
        final Asset asset = assetsService.getAssetById(assetId);
        final BigDecimal currentAssetPrice = assetsService.getLatestAssetPrice(assetId);
        final BigDecimal currentCurrencyPrice = getLatestPrice(asset.currency() + "/PLN");

        for (SingleTransactionRecord str : transactionRecords) {
            final BigDecimal currentValue = str.getNumberOfActive().multiply(currentAssetPrice).multiply(currentCurrencyPrice);
            final BigDecimal cost = str.getNumberOfActive().multiply(str.getTransaction().getPrice().multiply(str.getTransaction().getCurrencyExchangeRate()));
            str.setActiveGain(currentValue.subtract(cost));
        }
    }

    private BigDecimal getLatestPrice(String symbol) {
        return assetsService.getAssetWithPriceByAssetSymbol(symbol).stream()
                .sorted(Comparator.comparing(AssetPrice::getDateTime, Comparator.nullsFirst(Comparator.naturalOrder())).reversed())
                .map(AssetPrice::getPrice)
                .filter(Objects::nonNull)
                .findFirst().orElseGet(() -> {
                    return BigDecimal.ZERO;
                });
    }

    private void enrichSingleTransactionRecordsOfAllGain(final List<SingleTransactionRecord> transactionRecords) {
        transactionRecords.forEach(transaction -> transaction.setAllGain(transaction.getRealizedGain().add(transaction.getActiveGain())));
        for (SingleTransactionRecord str : transactionRecords) {
            final BigDecimal cost = str.getTransaction().getNumberOfAsset().multiply(str.getTransaction().getPrice().multiply(str.getTransaction().getCurrencyExchangeRate()));
            final BigDecimal gain = str.getAllGain();
            final BigDecimal percentageGain = PositionGainCalculator.getPercentageGain(cost, gain);
            str.setPercentageGain(percentageGain);
        }
    }

    private void enrichSingleTransactionRecordsOfPart(final List<SingleTransactionRecord> transactionRecords) {
        final Optional<BigDecimal> assetNumber = transactionRecords.stream()
                .filter(t -> t.getTransaction().getTransactionType() == TransactionType.BUY)
                .map(SingleTransactionRecord::getNumberOfActive)
                .reduce(BigDecimal::add);
        if (assetNumber.isPresent() && assetNumber.get().compareTo(BigDecimal.ZERO) > 0) {
            for (SingleTransactionRecord str : transactionRecords) {
                str.setPart(str.getNumberOfActive().divide(assetNumber.get(), 6, RoundingMode.FLOOR).multiply(BigDecimal.valueOf(100)));
            }
        }
    }
}
