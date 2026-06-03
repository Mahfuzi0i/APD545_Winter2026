/**********************************************
 Workshop 4&5
 Course: APD545 - Semester Winter 2026
 Last Name: Mahfuz
 First Name: Abdullah Al
 ID:180377236
 Section:NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature: Abdullah Al Mahfuz
 Date: 24 Feb 2026
 **********************************************/
package nexus.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.inject.Inject;
import nexus.di.AppInjector;
import nexus.service.AuthenticationService;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @Inject
    private AuthenticationService authService;

    @FXML
    private void initialize() {
        errorLabel.setVisible(false);
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText() == null ? "" : usernameField.getText().trim();
        String password = passwordField.getText() == null ? "" : passwordField.getText().trim();

        if (!authService.authenticate(username, password)) {
            errorLabel.setVisible(true);
            return;
        }

        errorLabel.setVisible(false);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            loader.setController(AppInjector.controllerFactory().call(DashboardController.class));
            loader.setControllerFactory(AppInjector.controllerFactory());
            Scene scene = new Scene(loader.load());

            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Nexus Portfolio Dashboard");
            dashboardStage.setScene(scene);
            dashboardStage.show();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            errorLabel.setText("Unable to open dashboard");
            errorLabel.setVisible(true);
        }
    }
}
