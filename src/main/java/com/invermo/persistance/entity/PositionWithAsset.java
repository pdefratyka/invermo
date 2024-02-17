package com.invermo.persistance.entity;

import com.invermo.persistance.enumeration.AssetType;
import com.invermo.persistance.enumeration.Currency;
import com.invermo.persistance.enumeration.PositionType;
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
public class PositionWithAsset {
    private Long positionId;
    private Long assetId;
    private PositionType positionType;
    private String assetName;
    private String assetSymbol;
    private AssetType assetType;
    private Currency currency;
}
