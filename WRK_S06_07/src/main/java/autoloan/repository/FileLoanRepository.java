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

import autoloan.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * File-I/O implementation of ILoanRepository.
 * Serializes Loan data to/from a local JSON file using Gson.
 * Bound in AppModule as the @Named("file") ILoanRepository.
 *
 * Because JavaFX Property objects are not directly serializable,
 * we use the inner LoanDto (plain POJO) as the serialization target.
 */
public class FileLoanRepository implements ILoanRepository {

    private static final String FILE_PATH = "autoloan_loans.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // -------------------------------------------------------------------------
    // ILoanRepository contract
    // -------------------------------------------------------------------------

    @Override
    public void saveLoan(Loan loan) {
        List<LoanDto> existing = loadDtos();
        existing.add(LoanDto.fromLoan(loan));
        writeDtos(existing);
    }

    @Override
    public List<Loan> getAllLoans() {
        List<LoanDto> dtos = loadDtos();
        List<Loan> loans = new ArrayList<>();
        for (LoanDto dto : dtos) {
            loans.add(dto.toLoan());
        }
        return loans;
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    private List<LoanDto> loadDtos() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();

        try (Reader reader = new FileReader(file)) {
            Type listType = new TypeToken<List<LoanDto>>() {}.getType();
            List<LoanDto> result = gson.fromJson(reader, listType);
            return result != null ? result : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("FileLoanRepository.loadDtos error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void writeDtos(List<LoanDto> dtos) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(dtos, writer);
        } catch (IOException e) {
            System.err.println("FileLoanRepository.writeDtos error: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // DTO – plain POJO so Gson can serialize/deserialize without JavaFX issues
    // -------------------------------------------------------------------------

    private static class LoanDto {
        String customerName, customerPhone, customerCity, customerProvince;
        String vehicleType, vehicleAge;
        double vehiclePrice, downPayment, interestRate, estimatedPayment;
        int durationMonths;
        String frequency;

        static LoanDto fromLoan(Loan loan) {
            LoanDto dto = new LoanDto();
            Customer c = loan.getCustomer();
            Vehicle v = loan.getVehicle();
            dto.customerName = c.getName();
            dto.customerPhone = c.getPhone();
            dto.customerCity = c.getCity();
            dto.customerProvince = c.getProvince();
            dto.vehicleType = v.getType() != null ? v.getType().getLabel() : "";
            dto.vehicleAge = v.getAge() != null ? v.getAge().getLabel() : "";
            dto.vehiclePrice = v.getPrice();
            dto.downPayment = loan.getDownPayment();
            dto.interestRate = loan.getInterestRate();
            dto.durationMonths = loan.getDurationMonths();
            dto.frequency = loan.getFrequency() != null ? loan.getFrequency().getLabel() : "";
            dto.estimatedPayment = loan.getEstimatedPayment();
            return dto;
        }

        Loan toLoan() {
            Customer c = new Customer();
            c.setName(customerName);
            c.setPhone(customerPhone);
            c.setCity(customerCity);
            c.setProvince(customerProvince);

            Vehicle v = new Vehicle();
            v.setType(VehicleType.fromLabel(vehicleType));
            v.setAge(VehicleAge.fromLabel(vehicleAge));
            v.setPrice(vehiclePrice);

            Loan loan = new Loan(c, v);
            loan.setDownPayment(downPayment);
            loan.setInterestRate(interestRate);
            loan.setDurationMonths(durationMonths);
            loan.setFrequency(PaymentFrequency.fromLabel(frequency));
            loan.setEstimatedPayment(estimatedPayment);
            return loan;
        }
    }
}

