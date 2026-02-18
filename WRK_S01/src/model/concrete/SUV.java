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
import model.abstractClasses.PassengerVehicle;

public class SUV extends PassengerVehicle {

    public SUV(int currentMileage) {
        super(
                "SUV",
                "Family transport, off-road capability",
                45000.00,
                12000,
                450.00,
                "Hybrid",
                currentMileage
        );
    }
}
