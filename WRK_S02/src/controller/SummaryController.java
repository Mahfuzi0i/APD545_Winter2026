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

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import model.Vehicle;
import model.MaintenanceRecord;
import model.UsageLog;

import service.VehicleService;
import service.MaintenanceService;
import service.UsageService;

import java.util.List;
import java.util.Map;

public class SummaryController {

    // SERVICES ARE RECEIVED — NOT CREATED HERE
    private VehicleService vService;
    private MaintenanceService mService;
    private UsageService uService;

    // UI CONTROLS
    @FXML private ComboBox<String> dataTypeCombo;
    @FXML private TextArea displayArea;

    // INITIALIZE DROPDOWN
    @FXML
    public void initialize() {
        dataTypeCombo.getItems().addAll("Vehicles", "Maintenance", "Usage");
    }

    // METHOD CALLED FROM MAIN CONTROLLER
    public void setServices(VehicleService v, MaintenanceService m, UsageService u) {
        this.vService = v;
        this.mService = m;
        this.uService = u;
    }

    // LOAD DATA
    @FXML
    private void loadData() {
        String type = dataTypeCombo.getValue();
        displayArea.clear();

        if (type == null) return;

        if (type.equals("Vehicles")) {
            for (Vehicle v : vService.getAllVehicles()) {
                displayArea.appendText(v + "\n");
            }

        } else if (type.equals("Maintenance")) {
            for (Map.Entry<Vehicle, List<MaintenanceRecord>> entry : mService.getAllRecords().entrySet()) {
                displayArea.appendText(entry.getKey() + "\n");
                for (MaintenanceRecord r : entry.getValue()) {
                    displayArea.appendText("   - " + r.getDate() + " | " + r.getDescription() + " | $" + r.getCost() + "\n");
                }
            }

        } else if (type.equals("Usage")) {
            for (Map.Entry<Vehicle, List<UsageLog>> entry : uService.getAllLogs().entrySet()) {
                displayArea.appendText(entry.getKey() + "\n");
                for (UsageLog log : entry.getValue()) {
                    displayArea.appendText("   - " + log.getStartDate() + " to " + log.getEndDate() + " | " + log.getKilometersDriven() + " km\n");
                }
            }
        }
    }

    // CLOSE VIEW
    @FXML
    private void closeWindow(ActionEvent event) {
        // Get the window (stage) from the button click
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
