package com.invermo.persistence.entity;

import com.invermo.persistence.enumeration.PositionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PositionEntity {
    private Long id;
    private Long userId;
    private Long assetId;
    private PositionType positionType;
}
