package com.invermo.application.gui.statistics.view;

import com.invermo.application.gui.components.views.charts.line.InvermoBarChart;
import com.invermo.application.gui.components.views.charts.model.ChartPoint;
import com.invermo.application.gui.statistics.service.StatisticsService;
import com.invermo.application.service.ServiceManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StatisticsViewController implements Initializable {

    @FXML
    private InvermoBarChart gainChart;
    @FXML
    private InvermoBarChart cumulativeGainChart;
    private StatisticsService statisticsService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.statisticsService = ServiceManager.getStatisticsService();
        List<ChartPoint> chartPoints = statisticsService.getGainByMonths();

        gainChart.setChartData(chartPoints);
        gainChart.setTitleSide(Side.TOP);
        gainChart.setTitle("Gain");
        gainChart.getXAxis().setLabel("Date");

        List<ChartPoint> cumulativeGainChartPoints = statisticsService.getCumulativeGainByMonths();

        cumulativeGainChart.setChartData(cumulativeGainChartPoints);
        cumulativeGainChart.setTitleSide(Side.TOP);
        cumulativeGainChart.setTitle("Cumulative Gain");
        cumulativeGainChart.getXAxis().setLabel("Date");
    }
}
