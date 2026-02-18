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

public class MaintenanceRecord {
    private LocalDate date;
    private String description;
    private double cost;

    public MaintenanceRecord() {}

    public MaintenanceRecord(LocalDate date, String description, double cost) {
        this.date = date;
        this.description = description;
        this.cost = cost;
    }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }
}
