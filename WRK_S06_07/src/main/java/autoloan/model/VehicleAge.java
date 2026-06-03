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

public enum VehicleAge {
    NEW("New"),
    USED("Used");

    private final String label;

    VehicleAge(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static VehicleAge fromLabel(String value) {
        for (VehicleAge age : values()) {
            if (age.label.equalsIgnoreCase(value)) {
                return age;
            }
        }
        return NEW;
    }
}

