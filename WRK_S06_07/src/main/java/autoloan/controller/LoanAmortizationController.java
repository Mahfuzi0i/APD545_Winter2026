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

import autoloan.model.AmortizationEntry;
import autoloan.model.Loan;
import autoloan.service.AmortizationService;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class LoanAmortizationController {

    @FXML private TableView<AmortizationEntry> table;
    @FXML private TableColumn<AmortizationEntry, Number> monthCol;
    @FXML private TableColumn<AmortizationEntry, Number> interestCol;
    @FXML private TableColumn<AmortizationEntry, Number> principalCol;
    @FXML private TableColumn<AmortizationEntry, Number> balanceCol;

    private final AmortizationService amortizationService;

    @Inject
    public LoanAmortizationController(AmortizationService amortizationService) {
        this.amortizationService = amortizationService;
    }

    @FXML
    public void initialize() {
        monthCol.setCellValueFactory(data -> data.getValue().monthProperty());
        interestCol.setCellValueFactory(data -> data.getValue().interestProperty());
        principalCol.setCellValueFactory(data -> data.getValue().principalProperty());
        balanceCol.setCellValueFactory(data -> data.getValue().balanceProperty());
    }

    public void displayAmortization(Loan loan) {
        var rows = FXCollections.observableArrayList(
                amortizationService.generateSchedule(loan));
        table.setItems(rows);
    }

    @FXML
    public void closeWindow() {
        Stage stage = (Stage) table.getScene().getWindow();
        stage.close();
    }
}

