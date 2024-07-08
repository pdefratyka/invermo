package com.invermo.application.persistance.entity;

import com.invermo.application.persistance.enumeration.PositionType;
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
public class Position {
    private Long id;
    private Long userId;
    private Long assetId;
//    private List<Transaction> transactions;
    private PositionType positionType;
}
