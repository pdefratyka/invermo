package com.invermo.application.gui.components.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ToastMessageController implements Initializable {

    private ToastMessageType type;

    @FXML
    private Text status;
    @FXML
    private Text message;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setToastMessage(final ToastMessageType toastMessageType, final String message) {
        this.type = toastMessageType;
        this.message.setText(message);
        this.status.setText(toastMessageType.getText());
        setStatusColor();
    }

    private void setStatusColor() {
        if (ToastMessageType.FAILURE.equals(type)) {
            this.status.setFill(Color.RED);
        } else {
            this.status.setFill(Color.GREEN);
        }
    }
}
