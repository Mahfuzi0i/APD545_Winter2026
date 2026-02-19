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

package autoloan.repository;

import autoloan.model.Loan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LoanRepository {

    private ObservableList<Loan> loans = FXCollections.observableArrayList();

    public void saveLoan(Loan loan){
        loans.add(loan);
    }

    public ObservableList<Loan> getLoans(){
        return loans;
    }
}
