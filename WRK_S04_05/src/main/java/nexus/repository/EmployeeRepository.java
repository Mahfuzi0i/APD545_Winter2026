/**********************************************
 Workshop 4&5
 Course: APD545 - Semester Winter 2026
 Last Name: Mahfuz
 First Name: Abdullah Al
 ID:180377236
 Section:NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature: Abdullah Al Mahfuz
 Date: 24 Feb 2026
 **********************************************/
package nexus.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import nexus.model.Employee;
import nexus.model.ExternalConsultant;
import nexus.model.InternalStaff;

import java.util.List;

public class EmployeeRepository {
    private final ObservableList<Employee> allEmployees;

    public EmployeeRepository() {
        allEmployees = FXCollections.observableArrayList();
        seedData();
    }

    public ObservableList<Employee> findAll() {
        return allEmployees;
    }

    public FilteredList<Employee> filterBySkill(String skill) {
        if (skill == null || skill.isBlank() || "All".equalsIgnoreCase(skill)) {
            return new FilteredList<>(allEmployees, e -> true);
        }
        String key = skill.toLowerCase();
        return new FilteredList<>(
                allEmployees,
                e -> e.getSkills().stream().anyMatch(s -> s.toLowerCase().contains(key))
        );
    }

    private void seedData() {
        allEmployees.addAll(
                new InternalStaff(
                        "E-101",
                        "Amina Rahman",
                        "amina@nexus.com",
                        List.of("Java", "Spring", "SQL"),
                        120000
                ),
                new InternalStaff(
                        "E-102",
                        "Lucas Chen",
                        "lucas@nexus.com",
                        List.of("React", "TypeScript", "UX"),
                        105000
                ),
                new InternalStaff(
                        "E-103",
                        "Noah Patel",
                        "noah@nexus.com",
                        List.of("DevOps", "AWS", "Docker"),
                        115000
                ),
                new ExternalConsultant(
                        "E-201",
                        "Priya Singh",
                        "priya@partner.com",
                        List.of("AI", "Python", "ML"),
                        110,
                        "DataBridge Inc."
                ),
                new ExternalConsultant(
                        "E-202",
                        "Ethan Brooks",
                        "ethan@contractor.com",
                        List.of("Cybersecurity", "Network", "Compliance"),
                        95,
                        "SecureStack Ltd."
                )
        );
    }
}
