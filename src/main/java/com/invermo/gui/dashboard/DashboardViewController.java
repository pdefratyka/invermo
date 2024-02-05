package com.invermo.gui.dashboard;

import com.invermo.gui.Views;
import com.invermo.state.ApplicationState;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardViewController implements Initializable {

    @FXML
    private AnchorPane mainContainer;
    @FXML
    private AnchorPane mainContent;
    @FXML
    private Text userName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.userName.setText(ApplicationState.getUser().userName());
    }

    @FXML
    public void onLogoutButton() throws IOException {
        logout();
    }

    @FXML
    private void loadAssetsView() {
        loadContent(Views.ASSETS_VIEW_RESOURCE);
    }

    private void loadContent(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Pane newContent = loader.load();
            mainContent.getChildren().setAll(newContent);
        } catch (Exception ex) {
            throw new RuntimeException("Error during loading content", ex);
        }
    }

    private void logout() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Views.LOGIN_VIEW_RESOURCE));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1280, 800);
        getStage().setScene(scene);
    }

    private Stage getStage() {
        return ((Stage) mainContainer.getScene().getWindow());
    }
}