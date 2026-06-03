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

package autoloan.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Manages the SQLite database connection and schema initialization.
 * Provides a single connection point for all repositories.
 */
public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:autoloan.db";
    private static Connection connection;

    /** Returns (or opens) the single shared SQLite connection. */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
            initializeSchema();
        }
        return connection;
    }

    /** Creates tables if they do not already exist. */
    private static void initializeSchema() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Users table – stores hashed passwords (never plain-text)
            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS users (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        username TEXT NOT NULL UNIQUE,
                        password_hash TEXT NOT NULL
                    )""");

            // Loans table – all persistent loan data
            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS loans (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        customer_name     TEXT,
                        customer_phone    TEXT,
                        customer_city     TEXT,
                        customer_province TEXT,
                        vehicle_type      TEXT,
                        vehicle_age       TEXT,
                        vehicle_price     REAL,
                        down_payment      REAL,
                        interest_rate     REAL,
                        duration_months   INTEGER,
                        frequency         TEXT,
                        estimated_payment REAL
                    )""");
        }
    }
}

