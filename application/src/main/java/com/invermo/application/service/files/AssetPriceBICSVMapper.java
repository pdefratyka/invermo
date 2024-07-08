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
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AssetPriceBICSVMapper {

    final static String BEGINNING = "Date";

    public static List<AssetPrice> extractAssetPricesFromCSVFile(final String fileName, final Long assetId) {
        final List<AssetPrice> assetPrices = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(BEGINNING)) {
                    break;
                }
            }
            while ((line = br.readLine()) != null) {
                LocalDateTime dateTime = extractDateFromCsvLine(line);
                BigDecimal price = extractPriceFromCsvLine(line);
                assetPrices.add(new AssetPrice(assetId, dateTime, price));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return assetPrices;
    }

    private static BigDecimal extractPriceFromCsvLine(final String line) {
        String closePrice = line.split(",")[1].substring(1).split("\"")[0];
        return new BigDecimal(closePrice);
    }

    private static LocalDateTime extractDateFromCsvLine(String line) {
        String[] date = line.substring(1).split("\"")[0].split("/");
        int dayOfMonth = Integer.parseInt(date[1]);
        int month = Integer.parseInt(date[0]);
        int year = Integer.parseInt(date[2]);
        final LocalDate localDate = LocalDate.of(year, month, dayOfMonth);
        return localDate.atStartOfDay();
    }
}
