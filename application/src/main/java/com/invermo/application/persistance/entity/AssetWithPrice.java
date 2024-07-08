package com.invermo.application.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssetWithPrice {
    private Asset asset;
    private List<AssetPrice> assetPrice;
}
