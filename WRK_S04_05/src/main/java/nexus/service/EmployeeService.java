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
package nexus.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javax.inject.Inject;
import nexus.model.Employee;
import nexus.repository.EmployeeRepository;

import java.util.stream.Collectors;

public class EmployeeService {
    @Inject
    private EmployeeRepository employeeRepository;

    public EmployeeService() {}

    public ObservableList<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    public FilteredList<Employee> filterBySkill(String skill) {
        return employeeRepository.filterBySkill(skill);
    }

    public ObservableList<String> getAllSkillsWithAllOption() {
        ObservableList<String> skills = FXCollections.observableArrayList("All");
        employeeRepository.findAll().forEach(emp -> skills.addAll(emp.getSkills()));
        return FXCollections.observableArrayList(
                skills.stream().distinct().sorted().collect(Collectors.toList())
        );
    }
}
