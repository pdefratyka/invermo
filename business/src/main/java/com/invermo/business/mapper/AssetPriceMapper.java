package com.invermo.business.mapper;

import com.invermo.business.domain.AssetPrice;
import com.invermo.persistence.entity.AssetPriceEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AssetPriceMapper {

    public static List<AssetPriceEntity> mapToAssetPriceEntities(final List<AssetPrice> assetPrices) {
        final List<AssetPriceEntity> assetPriceEntities = new ArrayList<>();
        for (AssetPrice assetPrice : assetPrices) {
            AssetPriceEntity assetPriceEntity = new AssetPriceEntity(assetPrice.getAssetId(),
                    assetPrice.getDateTime(), assetPrice.getPrice());
            assetPriceEntities.add(assetPriceEntity);
        }
        return assetPriceEntities;
    }

    public static AssetPrice mapAssetPriceEntityToAssetPrice(final AssetPriceEntity assetPriceEntity) {
        return new AssetPrice(assetPriceEntity.getAssetId(), assetPriceEntity.getDateTime(), assetPriceEntity.getPrice());
    }
}
