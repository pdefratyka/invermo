package com.invermo.persistance.entity;

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
public class AssetPrice {
    private Long id;
    private Long assetId;
    private LocalDateTime dateTime;
    private BigDecimal price;
}
