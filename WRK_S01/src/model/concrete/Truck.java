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

package model.concrete;
import model.abstractClasses.CommercialVehicle;

public class Truck extends CommercialVehicle {

    public Truck(int currentMileage) {
        super(
                "Truck",
                "Heavy cargo, long-distance hauling",
                62000.00,
                15000,
                600.00,
                "Diesel",
                currentMileage
        );
    }
}
