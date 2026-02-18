package view;
import model.abstractClasses.Vehicle;
import java.text.NumberFormat;
import java.util.Locale;

public class FleetView {

    // Number formatters for commas and currency
    private static final NumberFormat NUMBER_FORMAT =
            NumberFormat.getNumberInstance(Locale.US);

    private static final NumberFormat CURRENCY_FORMAT =
            NumberFormat.getCurrencyInstance(Locale.US);

    // ---------------- Requirement 2 ----------------
    public void displayMostUrgentMaintenance(Vehicle v) {
        System.out.println("\n--: Requirement 2 :--");
        System.out.println("The vehicle requiring the most urgent maintenance is: " + v.getName());
        System.out.println(v.getName() + "'s purchase price is: "
                + CURRENCY_FORMAT.format(v.getPurchasePrice()));
        System.out.println(v.getName() + "'s primary function: " + v.getPrimaryFunction());
        System.out.println(v.getName() + "'s service interval: Every "
                + NUMBER_FORMAT.format(v.getServiceInterval()) + " km");
        System.out.println(v.getName() + "'s maintenance cost: "
                + CURRENCY_FORMAT.format(v.getMaintenanceCost()));
    }

    // ---------------- Requirement 3 ----------------
    public void displayVehiclesByPurchasePrice(Vehicle[] vehicles) {
        System.out.println("\n--: Requirement 3 :--");
        System.out.println("\nVehicles in Descending Order of Purchase Price:");
        for (Vehicle v : vehicles) {
            System.out.println(
                    v.getName() + " - " + CURRENCY_FORMAT.format(v.getPurchasePrice())
            );
        }
    }

    // ---------------- Requirement 4 ----------------
    public void displayVehiclesByCategoryHeader(String category) {
        System.out.println("\n--: Requirement 4 :--");
        System.out.println("Vehicles in " + category + " Category:");
    }

    // ---------------- Requirement 5 & 6 ----------------
    public void displayMaintenanceUrgency(Vehicle[] vehicles) {
        System.out.println("\n--: Requirement 5 & 6 :--");
        System.out.println("Vehicles sorted by maintenance urgency (closest to service interval first):");

        for (Vehicle v : vehicles) {
            System.out.println(
                    v.getName() + " (" +
                            NUMBER_FORMAT.format(v.getCurrentMileage()) + " km / " +
                            NUMBER_FORMAT.format(v.getServiceInterval()) + " km - " +
                            NUMBER_FORMAT.format(v.getRemainingKmToService()) +
                            " km remaining)"
            );
        }
    }
}