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

import autoloan.model.Customer;

public class CustomerController {

    public boolean validateCustomer(Customer customer) {
        if (customer == null) {
            return false;
        }
        return isValidName(customer.getName())
                && isValidPhone(customer.getPhone())
                && isValidCity(customer.getCity())
                && isValidProvince(customer.getProvince());
    }

    private boolean isValidName(String value) {
        return value != null && value.trim().matches("[A-Za-z ]+");
    }

    private boolean isValidPhone(String value) {
        return value != null && value.trim().matches("\\d{10}");
    }

    private boolean isValidCity(String value) {
        return value != null && value.trim().matches("[A-Za-z ]+");
    }

    private boolean isValidProvince(String value) {
        return value != null && !value.trim().isEmpty();
    }
}

