package com.invermo.application.gui.composition.view;

import com.invermo.application.gui.composition.service.CompositionService;
import com.invermo.application.service.ServiceManager;
import com.invermo.application.state.ApplicationState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class CompositionViewController implements Initializable {

    @FXML
    private PieChart countryChart;
    @FXML
    private PieChart continentChart;
    private CompositionService compositionService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initiateCountryChart();
        initiateContinentChart();
    }

    private void initiateCountryChart(){
        this.compositionService = ServiceManager.getCompositionService();
        final Map<String, BigDecimal> countryCompositionData = compositionService.getComposition(ApplicationState.getUser().id(), 2L);
        final ObservableList<PieChart.Data> countryChartData = FXCollections.observableArrayList();
        countryCompositionData.forEach((key, value) -> {
            if(value.doubleValue() > 0){
                countryChartData.add(new PieChart.Data(key, value.doubleValue()));
            }
        });

        countryChart.setLabelsVisible(true);
        countryChart.setData(countryChartData);

        countryChart.getData().forEach(data -> {
            double total = countryChart.getData().stream().mapToDouble(PieChart.Data::getPieValue).sum();
            data.nameProperty().set(data.getName() + " (" + String.format("%.1f%%", (data.getPieValue() / total) * 100) + ")");
        });
    }

    private void initiateContinentChart(){
        this.compositionService = ServiceManager.getCompositionService();
        final Map<String, BigDecimal> countryCompositionData = compositionService.getComposition(ApplicationState.getUser().id(), 1L);
        final ObservableList<PieChart.Data> countryChartData = FXCollections.observableArrayList();
        countryCompositionData.forEach((key, value) -> {
            if(value.doubleValue() > 0){
                countryChartData.add(new PieChart.Data(key, value.doubleValue()));
            }
        });

        countryChart.setLabelsVisible(true);
        continentChart.setData(countryChartData);

        continentChart.getData().forEach(data -> {
            double total = continentChart.getData().stream().mapToDouble(PieChart.Data::getPieValue).sum();
            data.nameProperty().set(data.getName() + " (" + String.format("%.1f%%", (data.getPieValue() / total) * 100) + ")");
        });
    }
}
