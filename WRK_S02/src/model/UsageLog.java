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

package model;

import java.time.LocalDate;

public class UsageLog {
    private LocalDate startDate;
    private LocalDate endDate;
    private double kilometersDriven;

    public UsageLog() {}

    public UsageLog(LocalDate startDate, LocalDate endDate, double kilometersDriven) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.kilometersDriven = kilometersDriven;
    }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public double getKilometersDriven() { return kilometersDriven; }
    public void setKilometersDriven(double kilometersDriven) { this.kilometersDriven = kilometersDriven; }
}
