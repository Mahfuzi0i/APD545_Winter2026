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

package controller;
import java.util.Arrays;
import java.util.Scanner;
import model.abstractClasses.Vehicle;
import model.concrete.*;

public class FleetController {

    private int getValidMileage(Scanner scanner, String vehicleName) {
        int mileage;

        while (true) {
            System.out.print("Enter the current mileage for " + vehicleName + " (km): ");

            if (!scanner.hasNextInt()) {
                System.out.println("Mileage must be a numeric value. Please provide a valid Numeric value");
                scanner.next(); // discard invalid input
                continue;
            }

            mileage = scanner.nextInt();

            if (mileage < 0) {
                System.out.println("Mileage can not be negative. Please provide a positive value for Mileage");
                continue;
            }

            break;
        }
        return mileage;
    }

    public Vehicle[] createFleetFromUserInput() {
        Scanner scanner = new Scanner(System.in);

        int sedanKm = getValidMileage(scanner, "Sedan");
        int suvKm = getValidMileage(scanner, "SUV");
        int truckKm = getValidMileage(scanner, "Truck");
        int vanKm = getValidMileage(scanner, "Van");
        int ambulanceKm = getValidMileage(scanner, "Ambulance");

        Vehicle[] fleet = new Vehicle[5];
        fleet[0] = new Sedan(sedanKm);
        fleet[1] = new SUV(suvKm);
        fleet[2] = new Truck(truckKm);
        fleet[3] = new Van(vanKm);
        fleet[4] = new Ambulance(ambulanceKm);

        return fleet;
    }

    // Requirement 2
    public Vehicle getMostUrgentMaintenanceVehicle(Vehicle[] fleet) {
        Vehicle mostUrgent = fleet[0];

        for (int i = 1; i < fleet.length; i++) {
            if (fleet[i].compareTo(mostUrgent) < 0) {
                mostUrgent = fleet[i];
            }
        }
        return mostUrgent;
    }

    // Requirement 3
    public Vehicle[] sortByPurchasePriceDescending(Vehicle[] fleet) {
        Vehicle[] sorted = fleet.clone();

        Arrays.sort(sorted, (v1, v2) ->
                Double.compare(v2.getPurchasePrice(), v1.getPurchasePrice())
        );

        return sorted;
    }

    // Requirement 5 & 6
    public Vehicle[] sortByMaintenanceUrgency(Vehicle[] fleet) {
        Vehicle[] sorted = fleet.clone();
        Arrays.sort(sorted); // uses compareTo()
        return sorted;
    }

    // Requirement 4
    public void displayVehiclesByCategory(Vehicle[] fleet, String category) {
        for (Vehicle v : fleet) {
            if (v.getCategory().equalsIgnoreCase(category)) {
                System.out.println(
                        v.getName() +
                                " - Primary Function: " + v.getPrimaryFunction() +
                                " - Fuel Type: " + v.getFuelType()
                );
            }
        }
    }
}
