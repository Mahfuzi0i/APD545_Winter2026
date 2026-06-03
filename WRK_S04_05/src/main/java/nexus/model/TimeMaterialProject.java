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

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class TimeMaterialProject extends Project {
    private final DoubleProperty hourlyCap;
    private final DoubleBinding burnRate;

    public TimeMaterialProject(String id, String title, Status status, double hourlyCap) {
        super(id, title, status);
        this.hourlyCap = new SimpleDoubleProperty(hourlyCap);
        this.burnRate = Bindings.createDoubleBinding(
                () -> {
                    double totalHours = getAssignments().stream()
                            .mapToDouble(Assignment::getAllocatedHours)
                            .sum();
                    if (getHourlyCap() <= 0.0) {
                        return 0.0;
                    }
                    return totalHours / getHourlyCap();
                },
                this.hourlyCap,
                getAssignments()
        );
    }

    public double getHourlyCap() {
        return hourlyCap.get();
    }

    public void setHourlyCap(double hourlyCap) {
        this.hourlyCap.set(hourlyCap);
    }

    public DoubleProperty hourlyCapProperty() {
        return hourlyCap;
    }

    public DoubleBinding burnRateProperty() {
        return burnRate;
    }

    @Override
    public double getProfitMargin() {
        return 0.0;
    }
}
