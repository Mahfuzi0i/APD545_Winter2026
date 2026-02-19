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

public class Loan {

    private final ObjectProperty<Customer> customer = new SimpleObjectProperty<>();
    private final ObjectProperty<Vehicle> vehicle = new SimpleObjectProperty<>();

    private final DoubleProperty downPayment = new SimpleDoubleProperty();
    private final DoubleProperty interestRate = new SimpleDoubleProperty();
    private final IntegerProperty durationMonths = new SimpleIntegerProperty();
    private final StringProperty frequency = new SimpleStringProperty();
    private final DoubleProperty estimatedPayment = new SimpleDoubleProperty();

    public Loan(Customer customer, Vehicle vehicle) {
        this.customer.set(customer);
        this.vehicle.set(vehicle);
    }

    public Customer getCustomer() { return customer.get(); }
    public void setCustomer(Customer value) { customer.set(value); }
    public ObjectProperty<Customer> customerProperty() { return customer; }

    public Vehicle getVehicle() { return vehicle.get(); }
    public void setVehicle(Vehicle value) { vehicle.set(value); }
    public ObjectProperty<Vehicle> vehicleProperty() { return vehicle; }

    public double getDownPayment() { return downPayment.get(); }
    public void setDownPayment(double value) { downPayment.set(value); }
    public DoubleProperty downPaymentProperty() { return downPayment; }

    public double getInterestRate() { return interestRate.get(); }
    public void setInterestRate(double value) { interestRate.set(value); }
    public DoubleProperty interestRateProperty() { return interestRate; }

    public int getDurationMonths() { return durationMonths.get(); }
    public void setDurationMonths(int value) { durationMonths.set(value); }
    public IntegerProperty durationMonthsProperty() { return durationMonths; }

    public String getFrequency() { return frequency.get(); }
    public void setFrequency(String value) { frequency.set(value); }
    public StringProperty frequencyProperty() { return frequency; }

    public double getEstimatedPayment() { return estimatedPayment.get(); }
    public void setEstimatedPayment(double value) { estimatedPayment.set(value); }
    public DoubleProperty estimatedPaymentProperty() { return estimatedPayment; }

    @Override
    public String toString() {
        String customerName = getCustomer() == null ? "Unknown" : getCustomer().getName();
        String vehicleType = getVehicle() == null ? "Vehicle" : getVehicle().getType();
        String paymentFrequency = getFrequency() == null ? "N/A" : getFrequency();
        return customerName + " | " + vehicleType + " | " + String.format("%.2f%%", getInterestRate())
                + " | " + getDurationMonths() + " months | " + paymentFrequency;
    }
}
