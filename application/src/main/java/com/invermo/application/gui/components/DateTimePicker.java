package com.invermo.application.gui.components;

import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimePicker extends HBox {

    private final DatePicker datePicker;
    private final Spinner<Integer> hourSpinner;
    private final Spinner<Integer> minuteSpinner;

    public DateTimePicker() {
        this.datePicker = new DatePicker();
        this.hourSpinner = new Spinner<>(0, 23, 0);
        this.minuteSpinner = new Spinner<>(0, 59, 0);

        configureSpinners();

        getChildren().addAll(datePicker, new Label(" "), hourSpinner, new Label(":"), minuteSpinner);

        datePicker.setPrefWidth(250);
        hourSpinner.setPrefWidth(75);
        minuteSpinner.setPrefWidth(75);
    }

    private void configureSpinners() {
        // Hour spinner
        hourSpinner.setEditable(true);
        hourSpinner.getEditor().setAlignment(Pos.CENTER);
        hourSpinner.getEditor().setTextFormatter(new TextFormatter<>(c ->
                (c.getControlNewText().matches("\\d*") && c.getControlNewText().length() <= 2) ? c : null));

        // Minute spinner
        minuteSpinner.setEditable(true);
        minuteSpinner.getEditor().setAlignment(Pos.CENTER);
        minuteSpinner.getEditor().setTextFormatter(new TextFormatter<>(c ->
                (c.getControlNewText().matches("\\d*") && c.getControlNewText().length() <= 2) ? c : null));
    }

    public LocalDateTime getDateTime() {
        LocalDate date = datePicker.getValue();
        int hour = hourSpinner.getValue();
        int minute = minuteSpinner.getValue();
        return LocalDateTime.of(date, LocalTime.of(hour, minute));
    }
}
