package com.invermo.business.service;

import com.invermo.business.domain.Asset;
import com.invermo.business.domain.AssetPrice;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class AssetPriceService {

    private static final Logger logger = Logger.getLogger(AssetPriceService.class.getName());

    private final AssetService assetsService;
    private List<AssetPrice> latestAssetPrices;

    public AssetPriceService(AssetService assetsService) {
        this.assetsService = assetsService;
    }


    public Map<String, Long> updateAllAssetsPricesFromOneFile() throws GeneralSecurityException, IOException {
        return updatePricesWithGoogleSheet();
    }

    private Map<String, Long> updatePricesWithGoogleSheet() throws GeneralSecurityException, IOException {
        final List<List<Object>> values = GoogleSheetIntegrator.runGettingDataFromGoogleSheet();
        final List<Asset> assets = assetsService.findAllAssets();
        final List<AssetPrice> assetPrices = AssetPriceSheetMapper.updateAssetPrices(values, assets);
        final List<AssetPrice> assetPricesToInsert = updateAssetPriceByAsset(assetPrices);
        assetsService.saveAssetPrice(assetPricesToInsert);
        return constructAssetUpdateResult(assetPricesToInsert, assets);
    }

    private List<AssetPrice> updateAssetPriceByAsset(final List<AssetPrice> assetPrices) {
        if (assetPrices.isEmpty()) {
            return List.of();
        }
        final List<AssetPrice> assetsPricesToInsert = new ArrayList<>();
        final Map<Long, List<AssetPrice>> groupedAssets = assetPrices.stream()
                .collect(Collectors.groupingBy(AssetPrice::getAssetId));
        for (List<AssetPrice> singleAssets : groupedAssets.values()) {
            singleAssets.sort(Comparator.comparing(AssetPrice::getDateTime));
            final LocalDateTime latestAssetPriceDate = getLatestAssetPrice(singleAssets.get(0).getAssetId());
            assetsPricesToInsert.addAll(singleAssets.stream()
                    .filter(assetPrice -> assetPrice.getDateTime().isAfter(latestAssetPriceDate))
                    .toList());
        }
        logger.info("Number of assets to update: " + assetsPricesToInsert.size());
        return assetsPricesToInsert;
    }

    private LocalDateTime getLatestAssetPrice(final Long assetId) {
        if (latestAssetPrices == null) {
            latestAssetPrices = assetsService.getLatestAssetsPrice();
        }
        return latestAssetPrices.stream()
                .filter(assetPrice -> assetPrice.getAssetId().equals(assetId))
                .map(AssetPrice::getDateTime)
                .findFirst()
                .orElse(LocalDateTime.MIN);
    }

    private Map<String, Long> constructAssetUpdateResult(final List<AssetPrice> assetPrices, final List<Asset> assets) {
        final Map<Long, Long> assetIdCounts = assetPrices.stream()
                .collect(Collectors.groupingBy(AssetPrice::getAssetId, Collectors.counting()));

        return assetIdCounts.entrySet().stream()
                .collect(Collectors.toMap(entrySet -> findAssetNameById(entrySet.getKey(), assets), Map.Entry::getValue));
    }

    private String findAssetNameById(final Long assetId, final List<Asset> assets) {
        return assets.stream()
                .filter(asset -> asset.assetId().equals(assetId))
                .map(Asset::name)
                .findFirst()
                .orElse("");
    }
}
