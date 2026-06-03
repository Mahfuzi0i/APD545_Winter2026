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

package autoloan.repository;

import autoloan.model.User;

/**
 * Data-Access contract for user persistence.
 * Concrete implementations: SqlUserRepository.
 */
public interface IUserRepository {

    /** Persists a new user (password should already be hashed). */
    void addUser(User user);

    /** Retrieves a user by username, or null if not found. */
    User findUser(String username);
}

