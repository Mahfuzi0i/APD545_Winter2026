/**********************************************
 Workshop 2
 Course:APD545 - Semester 5
 Last Name:Mahfuz
 First Name:Abdullah Al
 ID:180377236
 Section:NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature - Abdullah
 Date:03 February 2026
 **********************************************/

package controller;

// FXML import statements
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import model.Vehicle;
import model.MaintenanceRecord;
import model.UsageLog;

import service.VehicleService;
import service.MaintenanceService;
import service.UsageService;

public class MainController {

    private VehicleService vService = new VehicleService();
    private MaintenanceService mService = new MaintenanceService();
    private UsageService uService = new UsageService();

    @FXML private TextField makeField, modelField, yearField;
    @FXML private ComboBox<String> typeCombo;
    @FXML private ComboBox<Vehicle> vehicleCombo;

    @FXML private DatePicker maintDate;
    @FXML private TextField descField, costField;

    @FXML private DatePicker startDatePicker, endDatePicker;
    @FXML private TextField kmField;

    @FXML
    public void initialize() {
        typeCombo.getItems().addAll("Sedan", "SUV", "Truck");
    }

    // CORE LOGIC - SAVE VEHICLE - EACH VEHICLE IS UNIQUE
    @FXML
    private void saveVehicle() {
        Vehicle v = new Vehicle(
                makeField.getText(),
                modelField.getText(),
                Integer.parseInt(yearField.getText()),
                typeCombo.getValue()
        );
        vService.addVehicle(v);
        vehicleCombo.getItems().add(v);
        makeField.clear(); modelField.clear(); yearField.clear(); typeCombo.setValue(null);
    }

    // CORE MAINTENANCE LOGIC - EACH MAINTENANCE RECORD IS UNIQUE TO A SINGLE VEHICLE
    @FXML
    private void saveMaintenance() {
        Vehicle v = vehicleCombo.getValue();
        if (v == null) return;

        MaintenanceRecord record = new MaintenanceRecord(
                maintDate.getValue(),
                descField.getText(),
                Double.parseDouble(costField.getText())
        );
        mService.addRecord(v, record);
        maintDate.setValue(null); descField.clear(); costField.clear();
    }

    // CORE USAGE LOGIC - EACH USAGE RECORD IS UNIQUE TO A SINGLE VEHICLE
    @FXML
    private void saveUsage() {
        Vehicle v = vehicleCombo.getValue();
        if (v == null) return;

        UsageLog log = new UsageLog(
                startDatePicker.getValue(),
                endDatePicker.getValue(),
                Double.parseDouble(kmField.getText())
        );
        uService.addLog(v, log);
        startDatePicker.setValue(null); endDatePicker.setValue(null); kmField.clear();
    }

    // OPEN A DETAIL WINDOW TO SELECT AND DISPLAY VEHICLE INFORMATION
    @FXML
    private void openSummary() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SummaryView.fxml"));
        Parent root = loader.load();

        SummaryController controller = loader.getController();
        controller.setServices(vService, mService, uService);

        Stage stage = new Stage();
        stage.setTitle("System Summary");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
