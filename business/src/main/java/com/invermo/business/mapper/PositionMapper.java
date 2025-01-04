package com.invermo.business.mapper;

import com.invermo.business.domain.Position;
import com.invermo.business.domain.PositionWithAsset;
import com.invermo.business.enumeration.AssetType;
import com.invermo.business.enumeration.Currency;
import com.invermo.persistence.entity.PositionEntity;
import com.invermo.persistence.entity.PositionWithAssetEntity;
import com.invermo.persistence.enumeration.PositionType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PositionMapper {

    public static PositionEntity mapPositionToPositionEntity(final Position position) {
        return PositionEntity.builder()
                .userId(position.getUserId())
                .assetId(position.getAssetId())
                .positionType(PositionType.valueOf(position.getPositionType().name()))
                .build();
    }

    public static Position mapPositionEntityToPosition(final PositionEntity positionEntity) {
        return new Position(positionEntity.getId(),
                positionEntity.getUserId(),
                positionEntity.getAssetId(),
                com.invermo.business.enumeration.PositionType.valueOf(positionEntity.getPositionType().name()));
    }

    public static List<Position> mapPositionEntitiesToPositions(final List<PositionEntity> positionEntities) {
        final List<Position> positions = new ArrayList<>();
        for (PositionEntity positionEntity : positionEntities) {
            Position position = mapPositionEntityToPosition(positionEntity);
            positions.add(position);
        }
        return positions;
    }

    public static PositionWithAsset mapPositionWithAssetEntityToPositionWithAsset(final PositionWithAssetEntity positionWithAssetEntity) {
        if (positionWithAssetEntity == null) {
            return null;
        }
        return PositionWithAsset.builder()
                .positionId(positionWithAssetEntity.getPositionId())
                .assetId(positionWithAssetEntity.getAssetId())
                .assetName(positionWithAssetEntity.getAssetName())
                .assetSymbol(positionWithAssetEntity.getAssetSymbol())
                .assetType(AssetType.fromName(positionWithAssetEntity.getAssetType().getName()))
                .positionType(com.invermo.business.enumeration.PositionType.valueOf(positionWithAssetEntity.getPositionType().name()))
                .currency(Currency.valueOf(positionWithAssetEntity.getCurrency().name()))
                .build();
    }

    public static List<PositionWithAsset> mapPositionWithAssetEntitiesToPositionsWithAssets(final List<PositionWithAssetEntity> positionWithAssets) {
        final List<PositionWithAsset> positions = new ArrayList<>();
        for (PositionWithAssetEntity positionWithAsset : positionWithAssets) {
            PositionWithAsset position = mapPositionWithAssetEntityToPositionWithAsset(positionWithAsset);
            positions.add(position);
        }
        return positions;
    }
}
