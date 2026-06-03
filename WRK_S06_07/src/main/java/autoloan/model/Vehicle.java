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

import javafx.beans.property.*;

public class Vehicle {

    private final ObjectProperty<VehicleType> type = new SimpleObjectProperty<>();
    private final ObjectProperty<VehicleAge> age = new SimpleObjectProperty<>();
    private final DoubleProperty price = new SimpleDoubleProperty();

    public VehicleType getType() { return type.get(); }
    public void setType(VehicleType value) { type.set(value); }
    public ObjectProperty<VehicleType> typeProperty() { return type; }

    public VehicleAge getAge() { return age.get(); }
    public void setAge(VehicleAge value) { age.set(value); }
    public ObjectProperty<VehicleAge> ageProperty() { return age; }

    public double getPrice() { return price.get(); }
    public void setPrice(double value) { price.set(value); }
    public DoubleProperty priceProperty() { return price; }
}

