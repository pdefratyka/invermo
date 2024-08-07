package com.invermo.application;

import com.invermo.application.gui.Views;
import com.invermo.application.state.ApplicationState;
import com.invermo.business.domain.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        User user=new User(1L,"qwe","qwe");
        ApplicationState.setUser(user);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Views.DASHBOARD_VIEW_RESOURCE));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1280, 800);
        stage.setTitle("Invermo");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}