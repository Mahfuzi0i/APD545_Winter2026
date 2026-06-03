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

package autoloan.app;

import autoloan.repository.*;
import autoloan.service.LoanCalculation;
import autoloan.service.FixedRateLoan;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * Google Guice Dependency Injection Configuration Module.
 *
 * Declares all interface-to-implementation bindings:
 *  - IUserRepository → SqlUserRepository
 *  - ILoanRepository @db   → SqlLoanRepository
 *  - ILoanRepository @file → FileLoanRepository
 *  - LoanCalculation → FixedRateLoan
 *
 * This replaces Workshop 3's manual AppConfig / AppInitializer approach.
 */
public class AppModule extends AbstractModule {

    @Override
    protected void configure() {

        // User persistence: SQLite only
        bind(IUserRepository.class)
                .to(SqlUserRepository.class);

        // Loan persistence: two concrete implementations distinguished by @Named
        bind(ILoanRepository.class)
                .annotatedWith(Names.named("db"))
                .to(SqlLoanRepository.class);

        bind(ILoanRepository.class)
                .annotatedWith(Names.named("file"))
                .to(FileLoanRepository.class);

        // Loan calculation strategy: fixed-rate formula
        bind(LoanCalculation.class)
                .to(FixedRateLoan.class);
    }
}

