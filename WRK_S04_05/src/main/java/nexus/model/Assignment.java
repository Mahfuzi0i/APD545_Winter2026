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
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Assignment {
    private final StringProperty role;
    private final DoubleProperty allocatedHours;
    private final SimpleObjectProperty<Employee> employee;

    public Assignment(String role, double allocatedHours, Employee employee) {
        this.role = new SimpleStringProperty(role);
        this.allocatedHours = new SimpleDoubleProperty(allocatedHours);
        this.employee = new SimpleObjectProperty<>(employee);
    }

    public String getRole() {
        return role.get();
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public StringProperty roleProperty() {
        return role;
    }

    public double getAllocatedHours() {
        return allocatedHours.get();
    }

    public void setAllocatedHours(double allocatedHours) {
        this.allocatedHours.set(allocatedHours);
    }

    public DoubleProperty allocatedHoursProperty() {
        return allocatedHours;
    }

    public DoubleProperty hoursProperty() {
        return allocatedHours;
    }

    public Employee getEmployee() {
        return employee.get();
    }

    public void setEmployee(Employee employee) {
        this.employee.set(employee);
    }

    public DoubleBinding getCost() {
        return Bindings.createDoubleBinding(
                () -> getAllocatedHours() * getEmployee().getBaseCost(),
                allocatedHours,
                employee
        );
    }
}
