package com.invermo.application.gui.statistics.service;

import com.invermo.application.facade.InnerApplicationFacade;
import com.invermo.application.gui.components.views.charts.model.ChartPoint;
import com.invermo.application.state.ApplicationState;
import com.invermo.business.domain.PositionGain;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class StatisticsService {
    public static final String GAIN = "GAIN";
    public static final String CUMULATIVE_GAIN = "CUMULATIVE_GAIN";
    private final InnerApplicationFacade innerApplicationFacade;

    public Map<String, List<ChartPoint>> getGainByMonths() {
        final Map<String, List<ChartPoint>> gains = new HashMap<>();
        final PositionGain positionGain = innerApplicationFacade.getPositionsGainByUserId(ApplicationState.getUser().id());
        final List<ChartPoint> gainList = positionGain.getGainByMonth().entrySet()
                .stream()
                .map(entry -> new ChartPoint(entry.getKey().toString(), entry.getValue()))
                .toList();
        final List<ChartPoint> cumulativeGainList = positionGain.getCumulativeGainByMonth().entrySet()
                .stream()
                .map(entry -> new ChartPoint(entry.getKey().toString(), entry.getValue()))
                .toList();

        gains.put(GAIN, gainList);
        gains.put(CUMULATIVE_GAIN, cumulativeGainList);

        return gains;
    }
}
