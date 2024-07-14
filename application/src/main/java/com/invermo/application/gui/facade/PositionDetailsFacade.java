package com.invermo.application.gui.facade;

import com.invermo.application.facade.InnerApplicationFacade;
import com.invermo.application.gui.components.views.charts.model.ChartPoint;
import com.invermo.application.state.ApplicationState;
import com.invermo.business.domain.PositionGain;
import com.invermo.business.domain.SingleTransactionRecord;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class PositionDetailsFacade {

    private final InnerApplicationFacade innerApplicationFacade;

    public List<SingleTransactionRecord> getSingleTransactionsForPosition(final Long positionId) {
        return innerApplicationFacade.getSingleTransactionsForPosition(positionId, ApplicationState.getUser().id());
    }

    public List<ChartPoint> getGainByMonths(final Long positionId) {
        final PositionGain positionGain = innerApplicationFacade.getPositionGain(positionId, ApplicationState.getUser().id());
        return positionGain.getGainByMonth().entrySet()
                .stream()
                .map(entry -> new ChartPoint(entry.getKey().toString(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public List<ChartPoint> getCumulativeGainByMonths(final Long positionId) {
        final PositionGain positionGain = innerApplicationFacade.getPositionGain(positionId, ApplicationState.getUser().id());
        return positionGain.getCumulativeGainByMonth().entrySet()
                .stream()
                .map(entry -> new ChartPoint(entry.getKey().toString(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public List<ChartPoint> getPercentageGainByMonth(final Long positionId) {
        final PositionGain positionGain = innerApplicationFacade.getPositionGain(positionId, ApplicationState.getUser().id());
        return positionGain.getPercentageGainByMonth().entrySet()
                .stream()
                .map(entry -> new ChartPoint(entry.getKey().toString(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
