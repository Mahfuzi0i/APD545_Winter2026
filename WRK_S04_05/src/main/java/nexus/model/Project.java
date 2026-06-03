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

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class Project {
    private final StringProperty id;
    private final StringProperty title;
    private final ObjectProperty<Status> status;
    private final ObservableList<Assignment> assignments;

    protected Project(String id, String title, Status status) {
        this.id = new SimpleStringProperty(id);
        this.title = new SimpleStringProperty(title);
        this.status = new SimpleObjectProperty<>(status);
        this.assignments = FXCollections.observableArrayList(
                assignment -> new Observable[] { assignment.allocatedHoursProperty() }
        );
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public Status getStatus() {
        return status.get();
    }

    public void setStatus(Status status) {
        this.status.set(status);
    }

    public ObjectProperty<Status> statusProperty() {
        return status;
    }

    public ObservableList<Assignment> getAssignments() {
        return assignments;
    }

    public double getTotalCost() {
        return assignments.stream().mapToDouble(a -> a.getCost().get()).sum();
    }

    public abstract double getProfitMargin();

    @Override
    public String toString() {
        return getTitle();
    }
}
