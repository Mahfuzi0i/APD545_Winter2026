/***********************************************
 Workshop # 1
 Course: APD545NAA - Winter2026
 Last Name: Al Mahfuz
 First Name: Abdullah
 ID: 180377236
 This assignment represents my own work in accordance
 with Seneca Academic Policy.
 Date: January 16, 2026
 ***********************************************/

package model.abstractClasses;

import model.interfaces.IVehicleMaintenance;
import model.interfaces.IVehicleOperations;

public abstract class Vehicle implements IVehicleMaintenance, IVehicleOperations, Comparable<Vehicle> {

    private final String name;
    private final String primaryFunction;
    private final double purchasePrice;
    private final int serviceInterval;
    private final double maintenanceCost;
    private final String fuelType;
    private final int currentMileage;

    protected Vehicle(String name, String primaryFunction, double purchasePrice,
                      int serviceInterval, double maintenanceCost,
                      String fuelType, int currentMileage) {

        this.name = name;
        this.primaryFunction = primaryFunction;
        this.purchasePrice = purchasePrice;
        this.serviceInterval = serviceInterval;
        this.maintenanceCost = maintenanceCost;
        this.fuelType = fuelType;
        this.currentMileage = currentMileage;
    }

    // ---------------- Getters ----------------

    public String getName() {
        return name;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public int getCurrentMileage() {
        return currentMileage;
    }

    @Override
    public String getPrimaryFunction() {
        return primaryFunction;
    }

    @Override
    public int getServiceInterval() {
        return serviceInterval;
    }

    @Override
    public double getMaintenanceCost() {
        return maintenanceCost;
    }

    @Override
    public String getFuelType() {
        return fuelType;
    }

    // -------- Maintenance Logic --------

    @Override
    public int getRemainingKmToService() {
        return serviceInterval - currentMileage;
    }

    // -------- Comparable Logic --------
    // Smaller remaining km => more urgent maintenance
    @Override
    public int compareTo(Vehicle other) {
        return Integer.compare(this.getRemainingKmToService(), other.getRemainingKmToService());
    }

    // -------- Output --------
    @Override
    public String toString() {
        return name + " - $" + purchasePrice;
    }
}
