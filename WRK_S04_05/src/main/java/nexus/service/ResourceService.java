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
import javax.inject.Inject;
import nexus.model.Assignment;
import nexus.model.Employee;
import nexus.model.OverAllocationException;
import nexus.model.Project;
import nexus.repository.ProjectRepository;

public class ResourceService {
    @Inject
    private ProjectRepository projectRepository;

    public ResourceService() {}

    public ObservableList<Assignment> getAssignmentsByEmployee(Employee employee) {
        ObservableList<Assignment> result = FXCollections.observableArrayList();
        for (Project project : projectRepository.findAll()) {
            for (Assignment assignment : project.getAssignments()) {
                if (assignment.getEmployee().getId().equals(employee.getId())) {
                    result.add(assignment);
                }
            }
        }
        return result;
    }

    public ObservableList<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public void updateAssignmentHours(Assignment assignment, double hours) throws OverAllocationException {
        if (hours < 0) {
            throw new IllegalArgumentException("Hours cannot be negative.");
        }
        validateOverAllocation(assignment.getEmployee(), hours - assignment.getAllocatedHours());
        assignment.setAllocatedHours(hours);
    }

    public void assignTeamMember(Project project, Employee employee, double hours) throws OverAllocationException {
        assignTeamMember(project, employee, "Developer", hours);
    }

    public void assignTeamMember(Project project, Employee employee, String role, double hours)
            throws OverAllocationException {
        if (hours <= 0) {
            throw new IllegalArgumentException("Hours must be greater than zero.");
        }
        validateOverAllocation(employee, hours);
        project.getAssignments().add(new Assignment(role, hours, employee));
    }

    public void validateOverAllocation(Employee employee, double newHours) throws OverAllocationException {
        double currentTotal = getTotalAllocatedHours(employee);
        if (currentTotal + newHours > 40.0) {
            throw new OverAllocationException(
                    "Over-allocation detected for " + employee.getName() + ": total hours exceed 40."
            );
        }
    }

    private double getTotalAllocatedHours(Employee employee) {
        double total = 0.0;
        for (Project project : projectRepository.findAll()) {
            for (Assignment assignment : project.getAssignments()) {
                if (assignment.getEmployee().getId().equals(employee.getId())) {
                    total += assignment.getAllocatedHours();
                }
            }
        }
        return total;
    }
}
