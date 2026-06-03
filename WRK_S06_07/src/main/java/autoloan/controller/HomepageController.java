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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Presentation controller for the homepage screen.
 * No injected dependencies needed here; it simply navigates to Login/Signup.
 */
public class HomepageController {

    @FXML
    private void openLogin(ActionEvent event) throws IOException {
        loadView(event, "/autoloan/view/login.fxml", "Auto Loan System - Login");
    }

    @FXML
    private void openSignup(ActionEvent event) throws IOException {
        loadView(event, "/autoloan/view/signup.fxml", "Auto Loan System - Signup");
    }

    private void loadView(ActionEvent event, String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        loader.setControllerFactory(MainApp.getInjector()::getInstance);
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1400, 900));
        stage.setTitle(title);
        stage.centerOnScreen();
    }
}

