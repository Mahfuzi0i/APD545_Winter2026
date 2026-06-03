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
package nexus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nexus.di.AppInjector;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        AppInjector.configure();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/login.fxml"));
        loader.setControllerFactory(AppInjector.controllerFactory());
        Scene scene = new Scene(loader.load());
        stage.setTitle("Nexus Resource Allocator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
