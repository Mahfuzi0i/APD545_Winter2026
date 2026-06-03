/**********************************************
 Workshop 4&5
 Course: APD545 - Semester Winter 2026
 Last Name: Mahfuz
 First Name: Abdullah Al
 ID:180377236
 Section:NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature: Abdullah Al Mahfuz
 Date: 24 Feb 2026
 **********************************************/
package nexus.service;

public class AuthenticationService {
    public boolean authenticate(String user, String pass) {
        return "manager".equalsIgnoreCase(user) && "nexus123".equals(pass);
    }
}
