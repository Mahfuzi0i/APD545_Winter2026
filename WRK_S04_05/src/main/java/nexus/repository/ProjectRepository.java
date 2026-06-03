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
import javax.inject.Inject;
import nexus.model.Assignment;
import nexus.model.Employee;
import nexus.model.FixedPriceProject;
import nexus.model.Project;
import nexus.model.Status;
import nexus.model.TimeMaterialProject;

import java.util.List;

public class ProjectRepository {
    private final ObservableList<Project> allProjects;
    private boolean seeded;

    @Inject
    private EmployeeRepository employeeRepository;

    public ProjectRepository() {
        allProjects = FXCollections.observableArrayList();
        seeded = false;
    }

    public ObservableList<Project> findAll() {
        if (!seeded) {
            seedData();
            seeded = true;
        }
        return allProjects;
    }

    private void seedData() {
        List<Employee> employees = employeeRepository.findAll();
        Employee e1 = employees.get(0);
        Employee e2 = employees.get(1);
        Employee e3 = employees.get(2);
        Employee e4 = employees.get(3);
        Employee e5 = employees.get(4);

        Project p1 = new FixedPriceProject("P-001", "AI Upgrade", Status.ACTIVE, 150000, 0.15);
        p1.getAssignments().addAll(
                new Assignment("Lead Engineer", 12, e1),
                new Assignment("ML Specialist", 14, e4)
        );

        Project p2 = new TimeMaterialProject("P-002", "Web Revamp", Status.ACTIVE, 120);
        p2.getAssignments().addAll(
                new Assignment("Frontend Lead", 16, e2),
                new Assignment("Cloud Ops", 10, e3)
        );

        Project p3 = new FixedPriceProject("P-003", "Cloud Migration", Status.ACTIVE, 250000, 0.18);
        p3.getAssignments().addAll(
                new Assignment("DevOps Engineer", 18, e3),
                new Assignment("Consultant", 8, e5)
        );

        Project p4 = new TimeMaterialProject("P-004", "Security Audit", Status.ACTIVE, 90);
        p4.getAssignments().add(
                new Assignment("Security Consultant", 20, e5)
        );

        Project p5 = new FixedPriceProject("P-005", "Mobile Expansion", Status.PLANNING, 175000, 0.12);
        Project p6 = new TimeMaterialProject("P-006", "API Platform", Status.ACTIVE, 140);
        p6.getAssignments().add(new Assignment("Backend Engineer", 8, e1));
        Project p7 = new FixedPriceProject("P-007", "Data Lake", Status.CLOSED, 190000, 0.16);
        Project p8 = new TimeMaterialProject("P-008", "Legacy Modernization", Status.ACTIVE, 160);
        p8.getAssignments().add(new Assignment("Architect", 10, e1));
        Project p9 = new FixedPriceProject("P-009", "Compliance Portal", Status.ACTIVE, 110000, 0.10);
        p9.getAssignments().add(new Assignment("UI Engineer", 12, e2));
        Project p10 = new TimeMaterialProject("P-010", "Analytics Dashboard", Status.PLANNING, 100);

        allProjects.addAll(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
    }
}
