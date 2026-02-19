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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    @FXML
    public void handleLogin(ActionEvent event) {

        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if(username.isEmpty() || password.isEmpty()){
            messageLabel.setText("Please enter username and password.");
            new Alert(Alert.AlertType.WARNING, "Please enter username and password.").show();
            usernameField.requestFocus();
            return;
        }

        if(AppConfig.userRepository.validate(username, password)){
            openLoanApplication(event);
        }
        else{
            messageLabel.setText("Invalid credentials. Please register if you do not have an account.");
            new Alert(Alert.AlertType.ERROR, "Invalid credentials. Please try again.").show();
            usernameField.clear();
            passwordField.clear();
            usernameField.requestFocus();
        }
    }

    private void openLoanApplication(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/autoloan/view/loan.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root,1400,900));
            stage.setTitle("Loan Application");
            stage.centerOnScreen();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void backToHome(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/autoloan/view/homepage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root,1400,900));
            stage.setTitle("Homepage");
            stage.centerOnScreen();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void openSignup(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/autoloan/view/signup.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root,1400,900));
            stage.setTitle("Signup");
            stage.centerOnScreen();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
