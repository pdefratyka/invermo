package com.invermo.application.gui.statistics.service;

import com.invermo.application.facade.InnerApplicationFacade;
import com.invermo.application.gui.components.views.charts.model.ChartPoint;
import com.invermo.application.state.ApplicationState;
import com.invermo.business.domain.PositionGain;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class StatisticsService {
    private final InnerApplicationFacade innerApplicationFacade;

    public List<ChartPoint> getGainByMonths() {
        final PositionGain positionGain = innerApplicationFacade.getPositionsGainByUserId(ApplicationState.getUser().id());
        return positionGain.getGainByMonth().entrySet()
                .stream()
                .map(entry -> new ChartPoint(entry.getKey().toString(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public List<ChartPoint> getCumulativeGainByMonths() {
        final PositionGain positionGain = innerApplicationFacade.getPositionsGainByUserId(ApplicationState.getUser().id());
        return positionGain.getCumulativeGainByMonth().entrySet()
                .stream()
                .map(entry -> new ChartPoint(entry.getKey().toString(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
