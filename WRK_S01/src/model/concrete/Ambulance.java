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
import model.abstractClasses.SpecializedVehicle;

public class Ambulance extends SpecializedVehicle {

    public Ambulance(int currentMileage) {
        super(
                "Ambulance",
                "Emergency medical transport, life-saving",
                85000.00,
                8000,
                800.00,
                "Diesel",
                currentMileage
        );
    }
}
