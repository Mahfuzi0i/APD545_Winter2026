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

package autoloan.controller;

import autoloan.model.Vehicle;

public class VehicleController {

    public boolean validateVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            return false;
        }
        return vehicle.getType() != null
                && vehicle.getAge() != null
                && vehicle.getPrice() > 0;
    }
}

