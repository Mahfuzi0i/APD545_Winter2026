/**********************************************
 Workshop 06 & 07
 Course: APD545 - Semester Winter 2026
 Last Name: Mahfuz
 First Name: Abdullah Al
 ID: 180377236
 Section: NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature: Abdullah Al Mahfuz
 Date: 11 March 2026
 **********************************************/

package autoloan.controller;

import autoloan.app.MainApp;
import autoloan.service.UserService;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Handles user login.
 *
 * Delegates to UserService.authenticate() which uses BCrypt to compare
 * the entered plain-text password against the stored hash.
 * Controllers never touch a repository directly.
 */
public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    private final UserService userService;

    @Inject
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @FXML
    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter username and password.");
            new Alert(Alert.AlertType.WARNING, "Please enter username and password.").show();
            usernameField.requestFocus();
            return;
        }

        if (authenticate(username, password)) {
            openLoanApplication(event);
        } else {
            messageLabel.setText("Invalid credentials. Please register if you do not have an account.");
            new Alert(Alert.AlertType.ERROR, "Invalid credentials. Please try again.").show();
            usernameField.clear();
            passwordField.clear();
            usernameField.requestFocus();
        }
    }

    /** UML-aligned method – delegates BCrypt check to UserService. */
    public boolean authenticate(String username, String plainTextPassword) {
        return userService.authenticate(username, plainTextPassword);
    }

    private void openLoanApplication(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/autoloan/view/loan.fxml"));
            loader.setControllerFactory(MainApp.getInjector()::getInstance);
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1400, 900));
            stage.setTitle("Loan Application");
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void backToHome(ActionEvent event) {
        loadView(event, "/autoloan/view/homepage.fxml", "Homepage");
    }

    @FXML
    public void openSignup(ActionEvent event) {
        loadView(event, "/autoloan/view/signup.fxml", "Auto Loan System - Signup");
    }

    private void loadView(ActionEvent event, String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setControllerFactory(MainApp.getInjector()::getInstance);
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1400, 900));
            stage.setTitle(title);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

