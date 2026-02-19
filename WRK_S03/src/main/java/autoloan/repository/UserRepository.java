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

package autoloan.repository;

import autoloan.model.User;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private List<User> users = new ArrayList<>();

    public UserRepository() {
        // required hard-coded users
        users.add(new User("admin", "1234", "admin@email.com"));
        users.add(new User("guest", "1111", "guest@email.com"));
    }

    public void addUser(User user){
        users.add(user);
    }

    public boolean validate(String username, String password){
        return users.stream()
                .anyMatch(u -> u.getUsername().equals(username)
                        && u.getPassword().equals(password));
    }
}
