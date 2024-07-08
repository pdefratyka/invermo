package com.invermo.persistence.entity;

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
public class AssetPriceEntity {
    private Long id;
    private Long assetId;
    private LocalDateTime dateTime;
    private BigDecimal price;

    public AssetPriceEntity(Long assetId, LocalDateTime dateTime, BigDecimal price) {
        this.assetId = assetId;
        this.dateTime = dateTime;
        this.price = price;
    }
}
