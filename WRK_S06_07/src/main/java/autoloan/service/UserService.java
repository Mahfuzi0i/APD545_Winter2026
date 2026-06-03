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

package autoloan.service;

import autoloan.model.User;
import autoloan.repository.IUserRepository;
import com.google.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Business-Logic Layer for user authentication and registration.
 *
 * Sits between controllers and IUserRepository.
 * Responsible for:
 *  - Hashing plain-text passwords with BCrypt before storage.
 *  - Verifying login credentials against stored hashes.
 *
 * Controllers NEVER touch IUserRepository directly.
 */
public class UserService {

    private final IUserRepository userRepository;

    @Inject
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Registers a new user.
     * Hashes the plain-text password with BCrypt before delegating to the repo.
     *
     * @param user User whose password field is still plain text.
     * @param plainTextPassword the original password entered by the user.
     */
    public void register(User user, String plainTextPassword) {
        String hashedPassword = hashPassword(plainTextPassword);
        User secureUser = new User(user.getUsername(), hashedPassword, user.getEmail());
        userRepository.addUser(secureUser);
    }

    /**
     * Authenticates a login attempt.
     * Fetches the stored hash and verifies via BCrypt.compare.
     *
     * @param username the entered username.
     * @param plainTextPassword the entered (plain) password.
     * @return true if credentials match, false otherwise.
     */
    public boolean authenticate(String username, String plainTextPassword) {
        User stored = userRepository.findUser(username);
        if (stored == null) return false;
        return BCrypt.checkpw(plainTextPassword, stored.getPassword());
    }

    /**
     * Hashes a plain-text password using BCrypt with a generated salt.
     *
     * @param plainText the password to hash.
     * @return BCrypt hash string.
     */
    public String hashPassword(String plainText) {
        return BCrypt.hashpw(plainText, BCrypt.gensalt());
    }
}

