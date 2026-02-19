/**********************************************
 Workshop 03
 Course: APD545 - Semester Winter 2026
 Last Name: Mahfuz
 First Name: Abdullah Al
 ID:180377236
 Section:NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature: Abdullah Al Mahfuz
 Date:19 Feb 2026
 **********************************************/

package autoloan.controller;

import autoloan.app.AppConfig;
import autoloan.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SignupController {

    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmField;
    @FXML private Label messageLabel;

    @FXML
    public void handleSignup(){

        if(usernameField.getText().isEmpty() ||
                emailField.getText().isEmpty() ||
                passwordField.getText().isEmpty() ||
                confirmField.getText().isEmpty()){
            messageLabel.setText("All fields are required");
            return;
        }

        if(!passwordField.getText().equals(confirmField.getText())){
            messageLabel.setText("Passwords do not match");
            return;
        }

        User user = new User(
                usernameField.getText(),
                passwordField.getText(),
                emailField.getText()
        );

        AppConfig.userRepository.addUser(user);

        messageLabel.setText("Account created. Please login.");
        backToLogin();
    }

    @FXML
    public void backToLogin(){
        try{
            Stage stage = (Stage) usernameField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/autoloan/view/login.fxml"));
            stage.setScene(new Scene(loader.load(), 1400, 900));
            stage.setTitle("Auto Loan System - Login");
            stage.centerOnScreen();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
