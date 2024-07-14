package com.invermo.application.service.files;

import com.invermo.business.domain.AssetPrice;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AssetPriceAllCSVMapper {

    final static String BEGINNING = "Date";

    public static List<AssetPrice> extractAssetPricesFromCSVFile(final String fileName, final Map<String, Long> assetsIds) {
        final List<AssetPrice> assetPrices = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String symbol = extractSymbolFromCsvLine(line);
                LocalDateTime dateTime = extractDateFromCsvLine(line);
                BigDecimal price = extractPriceFromCsvLine(line);
                assetPrices.add(new AssetPrice(assetsIds.get(symbol), dateTime, price));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return assetPrices;
    }

    private static String extractSymbolFromCsvLine(final String line) {
        return line.split(",")[0];
    }

    private static BigDecimal extractPriceFromCsvLine(final String line) {
        String closePrice = line.split("\",")[1];
        closePrice = closePrice.replace("\"", "");
        closePrice = closePrice.replace(",", "");
        return new BigDecimal(closePrice);
    }

    private static LocalDateTime extractDateFromCsvLine(String line) {
        String[] date = line.split(",\"")[1].split(" ");
        int dayOfMonth = Integer.parseInt(date[1].split(",")[0]);
        int month = getMonthFromString(date[0]);
        int year = Integer.parseInt(date[2].split("\"")[0]);
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
