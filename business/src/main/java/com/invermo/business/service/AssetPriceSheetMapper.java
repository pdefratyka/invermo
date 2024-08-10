package com.invermo.business.service;

import com.invermo.business.domain.Asset;
import com.invermo.business.domain.AssetPrice;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class AssetPriceSheetMapper {

    public static List<AssetPrice> updateAssetPrices(final List<List<Object>> values, final List<Asset> assets) {
        final List<AssetPrice> assetsToUpdate = new ArrayList<>();

        for (List<Object> row : values) {
            if (row.size() == 3) {
                String assetSymbol = row.get(0).toString();
                String date = row.get(1).toString();
                String value = row.get(2).toString();

                if (!assetSymbol.isBlank() && !date.isBlank() && !value.isBlank()) {
                    final Optional<Long> assetId = findAssetIdByAssetSymbol(assets, assetSymbol);

                    if (assetId.isPresent()) {
                        AssetPrice assetPrice = AssetPrice.builder()
                                .assetId(assetId.get())
                                .dateTime(mapToDateTime(date))
                                .price(mapToPrice(value))
                                .build();
                        assetsToUpdate.add(assetPrice);
                    } else {
                        System.out.println("No asset found with symbol:" + assetSymbol);
                    }
                }
            }
        }
        return assetsToUpdate;
    }

    private static BigDecimal mapToPrice(String value) {
        value=value.replace(",", "");
        return new BigDecimal(value);
    }

    private static LocalDateTime mapToDateTime(final String date) {
        LocalDateTime localDateTime = extractDateFromCsvLine(date);
        return localDateTime;
    }

    private static Optional<Long> findAssetIdByAssetSymbol(final List<Asset> assets, final String assetSymbol) {
        return assets.stream()
                .filter(asset -> asset.symbol().equals(assetSymbol))
                .map(Asset::assetId)
                .findFirst();
    }

    private static LocalDateTime extractDateFromCsvLine(String line) {
        int dayOfMonth = Integer.parseInt(line.split(" ")[1].split(",")[0]);
        int month = getMonthFromString(line.split(" ")[0]);
        int year = Integer.parseInt(line.split(" ")[2]);
        final LocalDate localDate = LocalDate.of(year, month, dayOfMonth);
        return localDate.atStartOfDay();
    }

    private static int getMonthFromString(String month) {
        final Map<String, Integer> months = new HashMap<>();
        months.put("Jan", 1);
        months.put("Feb", 2);
        months.put("Mar", 3);
        months.put("Apr", 4);
        months.put("May", 5);
        months.put("Jun", 6);
        months.put("Jul", 7);
        months.put("Aug", 8);
        months.put("Sep", 9);
        months.put("Oct", 10);
        months.put("Nov", 11);
        months.put("Dec", 12);
        return months.get(month);
    }
}
