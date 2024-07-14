package com.invermo.application.gui.components.views.charts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class ChartPoint {
    private final String xData;
    private final BigDecimal yData;
}
