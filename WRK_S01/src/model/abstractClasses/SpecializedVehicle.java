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

public abstract class SpecializedVehicle extends Vehicle {

    protected SpecializedVehicle(String name, String primaryFunction, double purchasePrice,
                                 int serviceInterval, double maintenanceCost,
                                 String fuelType, int currentMileage) {

        super(name, primaryFunction, purchasePrice, serviceInterval,
                maintenanceCost, fuelType, currentMileage);
    }

    @Override
    public String getCategory() {
        return "SpecializedVehicles";
    }
}
