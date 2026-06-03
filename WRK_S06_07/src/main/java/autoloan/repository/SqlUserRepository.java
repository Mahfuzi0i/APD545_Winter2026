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

import autoloan.db.DatabaseManager;
import autoloan.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SQLite-backed implementation of IUserRepository.
 * Passwords stored here are always BCrypt hashes – never plain text.
 */
public class SqlUserRepository implements IUserRepository {

    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword()); // already hashed by UserService
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("SqlUserRepository.addUser error: " + e.getMessage());
        }
    }

    @Override
    public User findUser(String username) {
        String sql = "SELECT username, password_hash FROM users WHERE username = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        "" // email not stored in DB for this workshop
                );
            }
        } catch (SQLException e) {
            System.err.println("SqlUserRepository.findUser error: " + e.getMessage());
        }
        return null;
    }
}

