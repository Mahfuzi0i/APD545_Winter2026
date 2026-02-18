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

import java.util.Scanner;
import controller.FleetController;
import model.abstractClasses.Vehicle;
import view.FleetView;

public class Main {

    public static void main(String[] args) {

        FleetController controller = new FleetController();
        FleetView view = new FleetView();
        Scanner scanner = new Scanner(System.in);

        System.out.println("--: Requirement 1 :--");

        // Create fleet
        Vehicle[] fleet = controller.createFleetFromUserInput();

        // Requirement 2
        Vehicle urgent = controller.getMostUrgentMaintenanceVehicle(fleet);
        view.displayMostUrgentMaintenance(urgent);

        // Requirement 3
        Vehicle[] priceSorted = controller.sortByPurchasePriceDescending(fleet);
        view.displayVehiclesByPurchasePrice(priceSorted);

        // Requirement 4
        String category;

        while (true) {
            System.out.print("\nEnter a vehicle category (PassengerVehicles, CommercialVehicles, SpecializedVehicles): ");
            category = scanner.next();

            if (category.equalsIgnoreCase("PassengerVehicles") ||
                    category.equalsIgnoreCase("CommercialVehicles") ||
                    category.equalsIgnoreCase("SpecializedVehicles")) {
                break;
            }

            System.out.println("Invalid category name. Please enter a valid vehicle category.");
        }

        view.displayVehiclesByCategoryHeader(category);
        controller.displayVehiclesByCategory(fleet, category);

        // Requirement 5 & 6
        Vehicle[] urgencySorted = controller.sortByMaintenanceUrgency(fleet);
        view.displayMaintenanceUrgency(urgencySorted);

        scanner.close();
    }
}
