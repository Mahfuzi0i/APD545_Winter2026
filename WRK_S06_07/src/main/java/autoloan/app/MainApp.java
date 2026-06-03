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

package autoloan.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Application entry point.
 *
 * Initialises the Google Guice injector (IoC container) and wires every
 * FXMLLoader to use it as its controller factory – meaning Guice creates
 * and injects all controllers automatically.
 */
public class MainApp extends Application {

    /** Shared injector – used by all controllers that open new views. */
    private static Injector injector;

    public static Injector getInjector() {
        return injector;
    }

    @Override
    public void start(Stage stage) throws Exception {
        initGuice();

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/autoloan/view/homepage.fxml"));

        // Tell FXMLLoader to ask Guice for every controller it needs
        loader.setControllerFactory(injector::getInstance);

        Scene scene = new Scene(loader.load(), 1400, 900);
        stage.setTitle("Auto Loan Application");
        stage.setScene(scene);
        stage.show();
    }

    /** Creates the Guice injector using AppModule bindings. */
    public void initGuice() {
        injector = Guice.createInjector(new AppModule());
    }

    public static void main(String[] args) {
        launch();
    }
}

