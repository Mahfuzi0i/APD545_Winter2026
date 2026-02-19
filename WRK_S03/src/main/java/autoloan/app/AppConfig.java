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

package autoloan.app;

import autoloan.repository.LoanRepository;
import autoloan.repository.UserRepository;
import autoloan.service.FixedRateLoan;
import autoloan.service.LoanCalculation;

public class AppConfig {

    public static final UserRepository userRepository = new UserRepository();
    public static final LoanRepository loanRepository = new LoanRepository();
    public static final LoanCalculation loanCalculation = new FixedRateLoan();
}
