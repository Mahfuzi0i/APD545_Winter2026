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
import autoloan.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SQLite-backed implementation of ILoanRepository.
 * Bound in AppModule as the @Named("db") ILoanRepository.
 */
public class SqlLoanRepository implements ILoanRepository {

    @Override
    public void saveLoan(Loan loan) {
        String sql = """
                INSERT INTO loans
                (customer_name, customer_phone, customer_city, customer_province,
                 vehicle_type, vehicle_age, vehicle_price,
                 down_payment, interest_rate, duration_months, frequency, estimated_payment)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Customer c = loan.getCustomer();
            Vehicle v = loan.getVehicle();

            stmt.setString(1, c.getName());
            stmt.setString(2, c.getPhone());
            stmt.setString(3, c.getCity());
            stmt.setString(4, c.getProvince());
            stmt.setString(5, v.getType() != null ? v.getType().getLabel() : "");
            stmt.setString(6, v.getAge() != null ? v.getAge().getLabel() : "");
            stmt.setDouble(7, v.getPrice());
            stmt.setDouble(8, loan.getDownPayment());
            stmt.setDouble(9, loan.getInterestRate());
            stmt.setInt(10, loan.getDurationMonths());
            stmt.setString(11, loan.getFrequency() != null ? loan.getFrequency().getLabel() : "");
            stmt.setDouble(12, loan.getEstimatedPayment());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("SqlLoanRepository.saveLoan error: " + e.getMessage());
        }
    }

    @Override
    public List<Loan> getAllLoans() {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM loans";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Customer c = new Customer();
                c.setName(rs.getString("customer_name"));
                c.setPhone(rs.getString("customer_phone"));
                c.setCity(rs.getString("customer_city"));
                c.setProvince(rs.getString("customer_province"));

                Vehicle v = new Vehicle();
                v.setType(VehicleType.fromLabel(rs.getString("vehicle_type")));
                v.setAge(VehicleAge.fromLabel(rs.getString("vehicle_age")));
                v.setPrice(rs.getDouble("vehicle_price"));

                Loan loan = new Loan(c, v);
                loan.setDownPayment(rs.getDouble("down_payment"));
                loan.setInterestRate(rs.getDouble("interest_rate"));
                loan.setDurationMonths(rs.getInt("duration_months"));
                loan.setFrequency(PaymentFrequency.fromLabel(rs.getString("frequency")));
                loan.setEstimatedPayment(rs.getDouble("estimated_payment"));

                loans.add(loan);
            }
        } catch (SQLException e) {
            System.err.println("SqlLoanRepository.getAllLoans error: " + e.getMessage());
        }
        return loans;
    }
}

