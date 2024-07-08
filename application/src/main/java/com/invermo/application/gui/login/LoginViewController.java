package com.invermo.application.gui.login;

import com.invermo.application.gui.Views;
import com.invermo.application.service.AuthenticationService;
import com.invermo.application.service.ServiceManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {

    @FXML
    private AnchorPane mainContainer;

    @FXML
    private TextField loginInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Text wrongCredentialsText;

    private AuthenticationService authenticationService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.authenticationService = ServiceManager.getAuthenticationService();
    }

    @FXML
    public void onPasswordKeyRelease(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            login();
        }
    }

    @FXML
    public void onLoginButtonClick() throws IOException {
        login();
    }

    private void loadMainDashboard() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Views.DASHBOARD_VIEW_RESOURCE));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1280, 800);
        getStage().setScene(scene);
    }

    private Stage getStage() {
        return ((Stage) mainContainer.getScene().getWindow());
    }

    private void login() throws IOException {
        final String login = loginInput.getText();
        final String password = passwordInput.getText();
        final boolean isSuccessfulLogin = authenticationService.login(login, password);
        if (isSuccessfulLogin) {
            handleSuccessfulLogin();
        } else {
            handleFailedLogin();
        }
    }

    private void handleSuccessfulLogin() throws IOException {
        loadMainDashboard();
    }

    private void handleFailedLogin() {
        loginInput.setText("");
        passwordInput.setText("");
        wrongCredentialsText.setVisible(true);
        loginInput.requestFocus();
    }
}
