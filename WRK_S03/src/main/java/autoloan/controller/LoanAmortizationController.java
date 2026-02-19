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

import autoloan.model.Loan;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class LoanAmortizationController {

    public static class Row {

        private final IntegerProperty month = new SimpleIntegerProperty();
        private final DoubleProperty interest = new SimpleDoubleProperty();
        private final DoubleProperty principal = new SimpleDoubleProperty();
        private final DoubleProperty balance = new SimpleDoubleProperty();

        public Row(int m, double i, double p, double b){
            month.set(m);
            interest.set(i);
            principal.set(p);
            balance.set(b);
        }

        public IntegerProperty monthProperty(){ return month; }
        public DoubleProperty interestProperty(){ return interest; }
        public DoubleProperty principalProperty(){ return principal; }
        public DoubleProperty balanceProperty(){ return balance; }
    }

    @FXML private TableView<Row> table;
    @FXML private TableColumn<Row, Number> monthCol;
    @FXML private TableColumn<Row, Number> interestCol;
    @FXML private TableColumn<Row, Number> principalCol;
    @FXML private TableColumn<Row, Number> balanceCol;

    @FXML
    public void initialize(){

        monthCol.setCellValueFactory(data -> data.getValue().monthProperty());
        interestCol.setCellValueFactory(data -> data.getValue().interestProperty());
        principalCol.setCellValueFactory(data -> data.getValue().principalProperty());
        balanceCol.setCellValueFactory(data -> data.getValue().balanceProperty());
    }

    public void loadLoan(Loan loan){

        double balance = loan.getVehicle().getPrice() - loan.getDownPayment();
        double annualRate = loan.getInterestRate() / 100.0;
        double monthlyRate = annualRate / 12.0;
        int months = loan.getDurationMonths();

        double payment =
                (balance * monthlyRate) /
                        (1 - Math.pow(1 + monthlyRate, -months));

        var rows = FXCollections.<Row>observableArrayList();

        for(int m = 1; m <= months; m++){

            double interest = balance * monthlyRate;
            double principal = payment - interest;
            balance -= principal;

            if(balance < 0) balance = 0;

            rows.add(new Row(
                    m,
                    round(interest),
                    round(principal),
                    round(balance)
            ));
        }

        table.setItems(rows);
    }

    private double round(double v){
        return Math.round(v * 100.0) / 100.0;
    }

    @FXML
    public void closeWindow(){
        Stage stage = (Stage) table.getScene().getWindow();
        stage.close();
    }
}
