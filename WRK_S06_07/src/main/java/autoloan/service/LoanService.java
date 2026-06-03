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

import autoloan.model.Loan;
import autoloan.repository.ILoanRepository;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.List;

/**
 * Business-Logic Layer for loan storage and retrieval.
 *
 * Holds two ILoanRepository references:
 *  - dbRepo  → SqlLoanRepository  (SQLite database)
 *  - fileRepo → FileLoanRepository (local JSON file)
 *
 * Controllers call this service; they never touch repositories directly.
 */
public class LoanService {

    private final ILoanRepository dbRepo;
    private final ILoanRepository fileRepo;

    @Inject
    public LoanService(
            @Named("db")   ILoanRepository dbRepo,
            @Named("file") ILoanRepository fileRepo) {
        this.dbRepo   = dbRepo;
        this.fileRepo = fileRepo;
    }

    // -------------------------------------------------------------------------
    // Database operations
    // -------------------------------------------------------------------------

    /** Saves the current loan to the SQLite database. */
    public void saveToDatabase(Loan loan) {
        dbRepo.saveLoan(loan);
    }

    /** Loads all loans from the SQLite database. */
    public List<Loan> loadFromDatabase() {
        return dbRepo.getAllLoans();
    }

    // -------------------------------------------------------------------------
    // File I/O operations
    // -------------------------------------------------------------------------

    /** Serializes the current loan and appends it to the local JSON file. */
    public void saveToFile(Loan loan) {
        fileRepo.saveLoan(loan);
    }

    /** Deserializes and returns all loans stored in the local JSON file. */
    public List<Loan> loadFromFile() {
        return fileRepo.getAllLoans();
    }
}

