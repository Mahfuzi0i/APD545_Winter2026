/**********************************************
 Workshop 4&5
 Course: APD545 - Semester Winter 2026
 Last Name: Mahfuz
 First Name: Abdullah Al
 ID:180377236
 Section:NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature: Abdullah Al Mahfuz
 Date: 24 Feb 2026
 **********************************************/
package nexus.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.List;

public class InternalStaff extends Employee {
    private final DoubleProperty annualSalary;

    public InternalStaff(
            String id,
            String name,
            String email,
            List<String> skills,
            double annualSalary
    ) {
        super(id, name, email, skills);
        this.annualSalary = new SimpleDoubleProperty(annualSalary);
    }

    public double getAnnualSalary() {
        return annualSalary.get();
    }

    public void setAnnualSalary(double annualSalary) {
        this.annualSalary.set(annualSalary);
    }

    public DoubleProperty annualSalaryProperty() {
        return annualSalary;
    }

    @Override
    public double getBaseCost() {
        return getAnnualSalary() / 52.0 / 40.0;
    }
}
