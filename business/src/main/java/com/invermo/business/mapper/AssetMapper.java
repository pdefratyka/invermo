package com.invermo.business.mapper;

import com.invermo.business.domain.Asset;
import com.invermo.business.enumeration.AssetType;
import com.invermo.business.enumeration.Currency;
import com.invermo.persistence.entity.AssetEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AssetMapper {

    public static Asset mapAssetEntityToAsset(final AssetEntity assetEntity) {
        return new Asset(assetEntity.assetId(), assetEntity.name(),
                assetEntity.symbol(),
                AssetType.fromName(assetEntity.type().getName()),
                Currency.valueOf(assetEntity.currency().name()));
    }

    public static AssetEntity mapAssetToAssentEntity(final Asset asset) {
        return new AssetEntity(asset.assetId(), asset.name(),
                asset.symbol(),
                com.invermo.persistence.enumeration.AssetType.fromName(asset.type().getName()),
                com.invermo.persistence.enumeration.Currency.valueOf(asset.currency().name()));
    }
}
