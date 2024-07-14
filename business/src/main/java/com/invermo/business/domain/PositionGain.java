package com.invermo.business.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Map;

@Getter
@Setter
public class PositionGain {
    private Position position;
    private Map<YearMonth, BigDecimal> gainByMonth;
    private Map<YearMonth, BigDecimal> cumulativeGainByMonth;
    private Map<YearMonth, BigDecimal> percentageGainByMonth;
}
