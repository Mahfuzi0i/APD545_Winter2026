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

import autoloan.model.Loan;
import java.util.List;

/**
 * Data-Access contract for loan persistence.
 * Concrete implementations: SqlLoanRepository (DB), FileLoanRepository (file I/O).
 */
public interface ILoanRepository {

    /** Persists a single loan. */
    void saveLoan(Loan loan);

    /** Retrieves all previously saved loans. */
    List<Loan> getAllLoans();
}

