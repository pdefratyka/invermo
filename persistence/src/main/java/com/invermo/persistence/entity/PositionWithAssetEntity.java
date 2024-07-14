package com.invermo.persistence.entity;

import com.invermo.persistence.enumeration.AssetType;
import com.invermo.persistence.enumeration.Currency;
import com.invermo.persistence.enumeration.PositionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PositionWithAssetEntity {
    private Long positionId;
    private Long assetId;
    private PositionType positionType;
    private String assetName;
    private String assetSymbol;
    private AssetType assetType;
    private Currency currency;
}
