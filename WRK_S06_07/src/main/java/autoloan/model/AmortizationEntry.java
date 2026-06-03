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

package autoloan.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class AmortizationEntry {

    private final IntegerProperty month = new SimpleIntegerProperty();
    private final DoubleProperty principal = new SimpleDoubleProperty();
    private final DoubleProperty interest = new SimpleDoubleProperty();
    private final DoubleProperty balance = new SimpleDoubleProperty();

    public AmortizationEntry(int month, double principal, double interest, double balance) {
        this.month.set(month);
        this.principal.set(principal);
        this.interest.set(interest);
        this.balance.set(balance);
    }

    public IntegerProperty monthProperty() { return month; }
    public DoubleProperty principalProperty() { return principal; }
    public DoubleProperty interestProperty() { return interest; }
    public DoubleProperty balanceProperty() { return balance; }
}

