package com.invermo.application.gui.portfolio.service;

import com.invermo.application.facade.InnerApplicationFacade;
import com.invermo.application.gui.components.views.charts.model.ChartPoint;
import com.invermo.application.state.ApplicationState;
import com.invermo.business.domain.PositionGain;
import com.invermo.business.domain.SingleTransactionRecord;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class PositionDetailsService {

    public static final String GAIN = "GAIN";
    public static final String CUMULATIVE_GAIN = "CUMULATIVE_GAIN";
    public static final String PERCENTAGE_GAIN = "PERCENTAGE_GAIN";

    private final InnerApplicationFacade innerApplicationFacade;

    public List<SingleTransactionRecord> getSingleTransactionsForPosition(final Long positionId) {
        return innerApplicationFacade.getSingleTransactionsForPosition(positionId, ApplicationState.getUser().id());
    }

    public Map<String, List<ChartPoint>> getPositionGainByMonths(final Long positionId) {
        final PositionGain positionGain = innerApplicationFacade.getPositionGain(positionId, ApplicationState.getUser().id());

        final List<ChartPoint> gainList = positionGain.getGainByMonth().entrySet()
                .stream()
                .map(entry -> new ChartPoint(entry.getKey().toString(), entry.getValue()))
                .toList();
        final List<ChartPoint> cumulativeGainList = positionGain.getCumulativeGainByMonth().entrySet()
                .stream()
                .map(entry -> new ChartPoint(entry.getKey().toString(), entry.getValue()))
                .toList();
        final List<ChartPoint> percentageGainList = positionGain.getPercentageGainByMonth().entrySet()
                .stream()
                .map(entry -> new ChartPoint(entry.getKey().toString(), entry.getValue()))
                .toList();

        final Map<String, List<ChartPoint>> gainMap = new HashMap<>();
        gainMap.put(GAIN, gainList);
        gainMap.put(CUMULATIVE_GAIN, cumulativeGainList);
        gainMap.put(PERCENTAGE_GAIN, percentageGainList);

        return gainMap;
    }
}
