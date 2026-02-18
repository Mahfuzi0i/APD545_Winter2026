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

public class Sedan extends PassengerVehicle {

    public Sedan(int currentMileage) {
        super(
                "Sedan",
                "Executive transportation, client visits",
                28500.00,
                10000,
                350.00,
                "Gasoline",
                currentMileage
        );
    }
}
