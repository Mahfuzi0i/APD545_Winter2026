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

import autoloan.model.AmortizationEntry;
import autoloan.model.Loan;

import java.util.ArrayList;
import java.util.List;

public class AmortizationService {

    public List<AmortizationEntry> generateSchedule(Loan loan) {
        List<AmortizationEntry> entries = new ArrayList<>();

        double balance = loan.getVehicle().getPrice() - loan.getDownPayment();
        double annualRate = loan.getInterestRate() / 100.0;
        double monthlyRate = annualRate / 12.0;
        int months = loan.getDurationMonths();

        double payment = (balance * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));

        for (int month = 1; month <= months; month++) {
            double interest = balance * monthlyRate;
            double principal = payment - interest;
            balance -= principal;
            if (balance < 0) {
                balance = 0;
            }
            entries.add(new AmortizationEntry(month, round(principal), round(interest), round(balance)));
        }
        return entries;
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}

