/**********************************************
 Workshop 03
 Course: APD545 - Semester Winter 2026
 Last Name: Mahfuz
 First Name: Abdullah Al
 ID:180377236
 Section:NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature: Abdullah Al Mahfuz
 Date:19 Feb 2026
 **********************************************/

package autoloan.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {

    private StringProperty username = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();

    public User(String username, String password, String email) {
        this.username.set(username);
        this.password.set(password);
        this.email.set(email);
    }

    public String getUsername() { return username.get(); }
    public StringProperty usernameProperty() { return username; }

    public String getPassword() { return password.get(); }
    public StringProperty passwordProperty() { return password; }

    public String getEmail() { return email.get(); }
    public StringProperty emailProperty() { return email; }
}
