package com.invermo.gui.components.views;

import com.invermo.gui.Views;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ToastMessageService {

    public static void displayToastMessage(final Stage primaryStage, final ToastMessageType toastMessageType,
                                           final String message) throws IOException {
        FXMLLoader loader = new FXMLLoader(ToastMessageService.class.getResource(Views.TOAST_MESSAGE));
        Parent root = loader.load();
        ToastMessageController toastMessageController = loader.getController();
        toastMessageController.setToastMessage(toastMessageType, message);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Result");
        stage.setScene(scene);
        stage.initOwner(primaryStage);
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }
}
