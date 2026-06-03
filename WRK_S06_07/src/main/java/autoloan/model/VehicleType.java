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

package autoloan.model;

public enum VehicleType {
    CAR("Car"),
    TRUCK("Truck"),
    FAMILY_VAN("Family Van");

    private final String label;

    VehicleType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static VehicleType fromLabel(String value) {
        for (VehicleType type : values()) {
            if (type.label.equalsIgnoreCase(value)) {
                return type;
            }
        }
        return CAR;
    }
}

