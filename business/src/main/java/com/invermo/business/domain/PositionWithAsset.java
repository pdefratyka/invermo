package com.invermo.business.domain;

import com.invermo.business.enumeration.AssetType;
import com.invermo.business.enumeration.Currency;
import com.invermo.business.enumeration.PositionType;
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
