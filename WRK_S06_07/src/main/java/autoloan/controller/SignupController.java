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
import autoloan.model.User;
import autoloan.service.UserService;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Handles new-user registration.
 *
 * Passes the plain-text password to UserService.register(),
 * which hashes it with BCrypt before saving to the SQLite database.
 * Plain-text passwords are NEVER stored.
 */
public class SignupController {

    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmField;
    @FXML private Label messageLabel;

    private final UserService userService;

    @Inject
    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @FXML
    public void handleSignup() {
        if (!validateSignup()) return;

        User user = new User(
                usernameField.getText().trim(),
                "",  // plain-text password not stored in model
                emailField.getText().trim()
        );

        // Service hashes the password with BCrypt before persisting
        saveUser(user, passwordField.getText());

        messageLabel.setText("Account created successfully. Please login.");
        backToLogin();
    }

    /** UML-aligned method – passes plain password to UserService for hashing. */
    public void saveUser(User user, String plainPassword) {
        userService.register(user, plainPassword);
    }

    /** UML-aligned method – validates form fields before registration. */
    public boolean validateSignup() {
        String username = usernameField.getText().trim();
        String email    = emailField.getText().trim();
        String password = passwordField.getText();
        String confirm  = confirmField.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            messageLabel.setText("All fields are required.");
            return false;
        }

        if (!email.contains("@")) {
            messageLabel.setText("Please enter a valid email address.");
            return false;
        }

        if (password.length() < 4) {
            messageLabel.setText("Password must be at least 4 characters.");
            return false;
        }

        if (!password.equals(confirm)) {
            messageLabel.setText("Passwords do not match.");
            return false;
        }

        return true;
    }

    @FXML
    public void backToLogin() {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/autoloan/view/login.fxml"));
            loader.setControllerFactory(MainApp.getInjector()::getInstance);
            stage.setScene(new Scene(loader.load(), 1400, 900));
            stage.setTitle("Auto Loan System - Login");
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

