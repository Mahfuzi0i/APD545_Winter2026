/**********************************************
 Workshop 03
 Course: APD545 - Semester Winter 2026
 Last Name: Mahfuz
 First Name: Abdullah Al
 ID:180377236
 Section:NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature: Abdullah Al Mahfuz
 Date:19 Feb 2026
 **********************************************/

package autoloan.model;

import javafx.beans.property.*;

public class Vehicle {

    private StringProperty type = new SimpleStringProperty();
    private StringProperty age = new SimpleStringProperty();
    private DoubleProperty price = new SimpleDoubleProperty();

    public String getType() { return type.get(); }
    public void setType(String value) { type.set(value); }
    public StringProperty typeProperty() { return type; }

    public String getAge() { return age.get(); }
    public void setAge(String value) { age.set(value); }
    public StringProperty ageProperty() { return age; }

    public double getPrice() { return price.get(); }
    public void setPrice(double value) { price.set(value); }
    public DoubleProperty priceProperty() { return price; }
}
