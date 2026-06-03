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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

public class ExternalConsultant extends Employee {
    private final DoubleProperty hourlyRate;
    private final StringProperty agencyName;

    public ExternalConsultant(
            String id,
            String name,
            String email,
            List<String> skills,
            double hourlyRate,
            String agencyName
    ) {
        super(id, name, email, skills);
        this.hourlyRate = new SimpleDoubleProperty(hourlyRate);
        this.agencyName = new SimpleStringProperty(agencyName);
    }

    public double getHourlyRate() {
        return hourlyRate.get();
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate.set(hourlyRate);
    }

    public DoubleProperty hourlyRateProperty() {
        return hourlyRate;
    }

    public String getAgencyName() {
        return agencyName.get();
    }

    public void setAgencyName(String agencyName) {
        this.agencyName.set(agencyName);
    }

    public StringProperty agencyNameProperty() {
        return agencyName;
    }

    @Override
    public double getBaseCost() {
        return getHourlyRate();
    }
}
