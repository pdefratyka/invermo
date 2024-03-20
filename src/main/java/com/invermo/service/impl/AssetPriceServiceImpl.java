package com.invermo.service.impl;

import com.invermo.persistance.entity.Asset;
import com.invermo.persistance.entity.AssetPrice;
import com.invermo.service.AssetPriceService;
import com.invermo.service.AssetsService;
import com.invermo.service.files.AssetPriceAllCSVMapper;
import com.invermo.service.files.AssetPriceBICSVMapper;
import com.invermo.service.files.AssetPriceCSVMapper;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class AssetPriceServiceImpl implements AssetPriceService {

    private static final Logger logger = Logger.getLogger(AssetPriceServiceImpl.class.getName());
    private static final String ASSET_PRICES_FILES_DIRECTORY = "C:\\Users\\pdefr\\OneDrive\\Pulpit\\workspace\\invermo\\src\\main\\resources\\prices";

    private final AssetsService assetsService;
    private List<AssetPrice> latestAssetPrices;

    public AssetPriceServiceImpl(AssetsService assetsService) {
        this.assetsService = assetsService;
    }

    public void updateAllAssetsPricesFromOneFile(final String fileName) {
        final List<Asset> assets = assetsService.getAllAssets();
        final Map<String, Long> assetsIds = assets.stream()
                .collect(Collectors.toMap(Asset::symbol, Asset::assetId));
        final List<AssetPrice> assetPrices = AssetPriceAllCSVMapper.extractAssetPricesFromCSVFile(fileName, assetsIds)
                .stream()
                .filter(assetPrice -> assetPrice.getAssetId() != null)
                .toList();
        final Map<Long, List<AssetPrice>> assetPriceMap = assetPrices.stream()
                .collect(Collectors.groupingBy(AssetPrice::getAssetId));
        final List<AssetPrice> assetPricesToInsert = new ArrayList<>();
        for (Map.Entry<Long, List<AssetPrice>> entry : assetPriceMap.entrySet()) {
            List<AssetPrice> assetPricesTemp = updateAssetPriceByAsset(entry.getValue());
            assetPricesToInsert.addAll(assetPricesTemp);
        }
        assetsService.saveAssetPrice(assetPricesToInsert);
    }


    @Override
    public void updateAllAssetsPrices() {
        logger.info("Update all assets prices");

        final List<String> fileNames = getAllAssetsPricesFilesNames();
        logger.info("Size of prices to update: " + fileNames.size());
        logger.info("Assets to update: " + String.join(", ", fileNames));
        final List<Asset> assets = assetsService.getAllAssets();
        final Map<String, Long> assetsToUpdate = getAssetsToUpdate(fileNames, assets);
        final List<AssetPrice> assetPricesToInsert = new ArrayList<>();
        for (Map.Entry<String, Long> entry : assetsToUpdate.entrySet()) {
            List<AssetPrice> assetPrices = updateAssetPrice(entry.getKey(), entry.getValue());
            assetPricesToInsert.addAll(assetPrices);
        }
        assetsService.saveAssetPrice(assetPricesToInsert);
    }

    private List<AssetPrice> updateAssetPrice(String fileName, Long assetId) {
        if (assetId < 0) {
            logger.warning("There is no asset matching file name: " + fileName);
            return List.of();
        } else {
            logger.info("Updating assets price for assetId: " + assetId + " and fileName: " + fileName);
            final String fullDirectory = ASSET_PRICES_FILES_DIRECTORY + "\\" + fileName;
            List<AssetPrice> assetPrices = new ArrayList<>();
            if (fileName.contains("-G.csv")) {
                assetPrices = AssetPriceCSVMapper.extractAssetPricesFromCSVFile(fullDirectory, assetId);
            } else if (fileName.contains("-BI.csv")) {
                assetPrices = AssetPriceBICSVMapper.extractAssetPricesFromCSVFile(fullDirectory, assetId);
            }
            System.out.println("Asset prices size: " + assetPrices.size());
            return updateAssetPriceByAsset(assetPrices);
        }
    }

    private List<String> getAllAssetsPricesFilesNames() {
        final File directory = new File(ASSET_PRICES_FILES_DIRECTORY);
        if (directory.exists() && directory.isDirectory()) {
            return Arrays.stream(Objects.requireNonNull(directory.listFiles()))
                    .map(File::getName)
                    .toList();
        } else {
            logger.warning("Invalid directory");
        }
        return List.of();
    }

    private Map<String, Long> getAssetsToUpdate(final List<String> fileNames, final List<Asset> assets) {
        return fileNames.stream()
                .collect(Collectors.toMap(fileName -> fileName, fileName -> getAssetIdByFileName(assets, fileName)));
    }

    private Long getAssetIdByFileName(final List<Asset> assets, String fileName) {
        fileName = fileName.replace("_", "/");
        final String assetSymbol = fileName.split("-")[0];
        return assets.stream()
                .filter(asset -> asset.symbol().equals(assetSymbol))
                .findFirst()
                .map(Asset::assetId)
                .orElse(-1L);
    }

    private List<AssetPrice> updateAssetPriceByAsset(List<AssetPrice> assetPrices) {
        if (assetPrices.isEmpty()) {
            return List.of();
        }
        final LocalDateTime latestAssetPriceDate = getLatestAssetPrice(assetPrices.get(0).getAssetId());
        assetPrices.sort(Comparator.comparing(AssetPrice::getDateTime));
        final List<AssetPrice> assetsPricesToInsert = assetPrices.stream()
                .filter(assetPrice -> assetPrice.getDateTime().isAfter(latestAssetPriceDate))
                .toList();
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
}
