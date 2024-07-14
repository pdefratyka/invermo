package com.invermo.application.gui.components.views.charts.line;

import com.invermo.application.gui.components.views.charts.model.ChartPoint;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class InvermoBarChart extends BarChart<String, Number> {

    private List<ChartPoint> points;
    private String percentageType;

    public InvermoBarChart() {
        super(new CategoryAxis(), new NumberAxis());
        setDefaultProperties();
    }

    public void setChartData(List<ChartPoint> chartPoints) {
        final List<XYChart.Series<String, Number>> series = new ArrayList<>();
        final XYChart.Series<String, Number> s1 = new XYChart.Series<>();
        s1.setData(FXCollections.observableArrayList());
        for (ChartPoint chartPoint : chartPoints) {
            s1.getData().add(new Data<>(chartPoint.getXData(), chartPoint.getYData()));
        }
        series.add(s1);

        // Add tooltips to each data node


        final ObservableList<XYChart.Series<String, Number>> observableList = FXCollections.observableArrayList();
        observableList.addAll(series);
        setData(observableList);

        //Supposedly there might be problem with displaying nodes without this layout.
        layout();

        for (XYChart.Data<String, Number> data : s1.getData()) {
            javafx.scene.Node node = data.getNode();
            if (node != null) {
                String tooltipData = "";
                if ("PERCENTAGE_TYPE".equals(percentageType)) {
                    tooltipData = data.getYValue().toString();
                } else {
                    tooltipData = formatToMoney(data.getYValue().toString());
                }
                Tooltip tooltip = new Tooltip(tooltipData);
                Tooltip.install(node, tooltip);

                if(data.getYValue().doubleValue()<0){
                    node.setStyle("-fx-bar-fill: red;");
                }else{
                    node.setStyle("-fx-bar-fill: green;");
                }

                // Optional: Add a style to the hovered node
                node.setOnMouseEntered(event -> {
                    node.setStyle("-fx-bar-fill: gray;");
                    tooltip.show(node, event.getScreenX(), event.getScreenY() + 10);
                });
                node.setOnMouseExited(event -> {
                    if(data.getYValue().doubleValue()<0){
                        node.setStyle("-fx-bar-fill: red;");
                    }else{
                        node.setStyle("-fx-bar-fill: green;");
                    }
                    tooltip.hide();
                });
            }
        }
    }

    private void setDefaultProperties() {
        setLegendVisible(false);
    }

    public void setPercentageType(String percentageType) {
        this.percentageType = percentageType;
    }

    private String formatToMoney(String toFormat) {
        // Parse the string to a double
        double value;
        try {
            value = Double.parseDouble(toFormat);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + toFormat);
            return "";
        }

        // Create a DecimalFormat instance
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getCurrencyInstance();

        // Customize the decimal format symbols
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setGroupingSeparator(' ');
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setCurrencySymbol("");

        // Apply the custom decimal format symbols
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);

        // Set the maximum fraction digits to 2 (for rounding)
        decimalFormat.setMaximumFractionDigits(2);

        // Format the value
        String formattedValue = decimalFormat.format(value);

        // Append the currency symbol at the end
        formattedValue += " PLN";

        // Print the formatted value
        System.out.println(formattedValue);
        return formattedValue;
    }
}
