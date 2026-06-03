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

package autoloan.service;

import autoloan.model.Loan;
import autoloan.model.Vehicle;

public class FixedRateLoan implements LoanCalculation {

    @Override
    public double calculatePayment(Loan loan, Vehicle vehicle) {
        double principal = vehicle.getPrice() - loan.getDownPayment();
        return calculatePayment(principal, loan.getInterestRate(), loan.getDurationMonths());
    }

    @Override
    public double calculatePayment(double principal, double annualRate, int months) {
        double monthlyRate = annualRate / 100 / 12;

        return (principal * monthlyRate) /
                (1 - Math.pow(1 + monthlyRate, -months));
    }
}

