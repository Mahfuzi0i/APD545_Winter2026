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

public class Van extends CommercialVehicle {

    public Van(int currentMileage) {
        super(
                "Van",
                "Passenger group transport, deliveries",
                38500.00,
                10000,
                400.00,
                "Gasoline",
                currentMileage
        );
    }
}
