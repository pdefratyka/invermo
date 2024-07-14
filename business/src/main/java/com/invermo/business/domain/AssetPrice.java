package com.invermo.business.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AssetPrice implements Comparable {
    private Long assetId;
    private LocalDateTime dateTime;
    private BigDecimal price;

    @Override
    public int compareTo(Object o) {
        if (o instanceof AssetPrice assetPrice) {
            if (assetPrice.getDateTime().isBefore(dateTime)) {
                return -1;
            }
            return 1;
        }
        return 0;
    }
}
