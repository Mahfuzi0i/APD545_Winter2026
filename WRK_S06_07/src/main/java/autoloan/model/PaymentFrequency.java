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

public enum PaymentFrequency {
    WEEKLY("Weekly"),
    BI_WEEKLY("Bi-weekly"),
    MONTHLY("Monthly");

    private final String label;

    PaymentFrequency(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static PaymentFrequency fromLabel(String value) {
        for (PaymentFrequency frequency : values()) {
            if (frequency.label.equalsIgnoreCase(value)) {
                return frequency;
            }
        }
        return MONTHLY;
    }
}

