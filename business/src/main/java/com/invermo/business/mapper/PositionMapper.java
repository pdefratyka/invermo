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

    public static List<Position> mapPositionEntitiesToPositions(final List<PositionEntity> positionEntities) {
        final List<Position> positions = new ArrayList<>();
        for (PositionEntity positionEntity : positionEntities) {
            Position position = new Position(positionEntity.getId(),
                    positionEntity.getUserId(),
                    positionEntity.getAssetId(),
                    com.invermo.business.enumeration.PositionType.valueOf(positionEntity.getPositionType().name()));
            positions.add(position);
        }
        return positions;
    }

    public static List<PositionWithAsset> mapPositionWithAssetEntitiesToPositionsWithAssets(final List<PositionWithAssetEntity> positionWithAssets) {
        final List<PositionWithAsset> positions = new ArrayList<>();
        for (PositionWithAssetEntity positionWithAsset : positionWithAssets) {
            PositionWithAsset position=PositionWithAsset.builder()
                    .positionId(positionWithAsset.getPositionId())
                    .assetId(positionWithAsset.getAssetId())
                    .assetName(positionWithAsset.getAssetName())
                    .assetSymbol(positionWithAsset.getAssetSymbol())
                    .assetType(AssetType.fromName(positionWithAsset.getAssetType().getName()))
                    .positionType( com.invermo.business.enumeration.PositionType.valueOf(positionWithAsset.getPositionType().name()))
                    .currency(Currency.valueOf(positionWithAsset.getCurrency().name()))
                    .build();
            positions.add(position);
        }
        return positions;
    }
}
