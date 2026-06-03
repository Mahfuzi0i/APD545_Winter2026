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
package nexus.controller;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javax.inject.Inject;
import nexus.model.Assignment;
import nexus.model.Employee;
import nexus.model.OverAllocationException;
import nexus.model.Project;
import nexus.model.Status;
import nexus.service.EmployeeService;
import nexus.service.ResourceService;

import java.util.LinkedHashSet;
import java.util.Set;

public class AllocatorController {
    @FXML
    private ComboBox<String> skillFilter;

    @FXML
    private ComboBox<Employee> employeeBox;

    @FXML
    private TextField roleField;

    @FXML
    private TextField hoursField;

    @FXML
    private Label projectedLoadLabel;

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TableView<WorkloadRow> workloadTable;

    @FXML
    private TableColumn<WorkloadRow, String> projectCol;

    @FXML
    private TableColumn<WorkloadRow, String> roleCol;

    @FXML
    private TableColumn<WorkloadRow, Double> currentHoursCol;

    @Inject
    private EmployeeService employeeService;

    @Inject
    private ResourceService resourceService;

    private final ObservableList<WorkloadRow> workloadRows = FXCollections.observableArrayList();

    private Project currentProject;

    @FXML
    private void initialize() {
        populateSkills();
        employeeBox.setItems(employeeService.findAllEmployees());

        projectCol.setCellValueFactory(cell -> cell.getValue().projectTitleProperty());
        roleCol.setCellValueFactory(cell -> cell.getValue().roleProperty());
        currentHoursCol.setCellValueFactory(cell -> cell.getValue().hoursProperty().asObject());
        currentHoursCol.setCellFactory(col -> new FocusCommitDoubleCell());
        currentHoursCol.setOnEditCommit(event -> {
            WorkloadRow row = event.getRowValue();
            Double newValueObj = event.getNewValue();
            if (newValueObj == null) {
                workloadTable.refresh();
                showError("Invalid hours", "Hours must be numeric.");
                return;
            }
            double newValue = newValueObj;
            if (newValue < 0) {
                workloadTable.refresh();
                showError("Invalid hours", "Hours cannot be negative.");
                return;
            }
            row.setHours(newValue);
            updateProjectedLoadState();
        });
        workloadTable.setEditable(true);
        workloadTable.setItems(workloadRows);

        skillFilter.valueProperty().addListener((obs, oldValue, newValue) -> applySkillFilter());
        employeeBox.valueProperty().addListener((obs, oldEmp, newEmp) -> loadCurrentWorkload(newEmp));
        roleField.textProperty().addListener((obs, oldText, newText) -> updateProjectedLoadState());
        hoursField.textProperty().addListener((obs, oldText, newText) -> updateProjectedLoadState());

        confirmButton.setDisable(true);
        projectedLoadLabel.setText("Projected Load: 0.0");
        confirmButton.setOnAction(event -> handleConfirm());
        cancelButton.setOnAction(event -> handleCancel());
    }

    public void setProject(Project project) {
        this.currentProject = project;
        updateProjectedLoadState();
    }

    private void handleConfirm() {
        Employee selectedEmployee = employeeBox.getValue();
        if (selectedEmployee == null) {
            showError("Missing employee", "Please select an employee.");
            return;
        }

        String role = roleField.getText() == null ? "" : roleField.getText().trim();
        if (role.isBlank()) {
            showError("Missing role", "Please enter a role.");
            return;
        }

        Double newHours = parseHours(hoursField.getText());
        if (newHours == null || newHours <= 0) {
            showError("Invalid hours", "Please enter a valid number of hours greater than zero.");
            return;
        }

        try {
            Set<String> updatedProjects = new LinkedHashSet<>();
            for (WorkloadRow row : workloadRows) {
                if (Double.compare(row.getAssignment().getAllocatedHours(), row.getHours()) != 0) {
                    resourceService.updateAssignmentHours(row.getAssignment(), row.getHours());
                    Project updatedProject = findProjectForAssignment(row.getAssignment());
                    if (updatedProject != null) {
                        updatedProjects.add(updatedProject.getTitle());
                    }
                }
            }
            resourceService.assignTeamMember(currentProject, selectedEmployee, role, newHours);
            updatedProjects.add(currentProject.getTitle());
            showInfo(
                    "Allocation Saved",
                    "Updated projects: " + String.join(", ", updatedProjects)
            );
            closeDialog();
        } catch (OverAllocationException | IllegalArgumentException ex) {
            showError("Allocation blocked", ex.getMessage());
        }
    }

    private void handleCancel() {
        closeDialog();
    }

    private void populateSkills() {
        skillFilter.setItems(employeeService.getAllSkillsWithAllOption());
        skillFilter.getSelectionModel().select("All");
    }

    private void applySkillFilter() {
        String selectedSkill = skillFilter.getValue();
        employeeBox.setItems(employeeService.filterBySkill(selectedSkill));
        if (!employeeBox.getItems().contains(employeeBox.getValue())) {
            employeeBox.getSelectionModel().clearSelection();
            workloadRows.clear();
        }
        updateProjectedLoadState();
    }

    private void loadCurrentWorkload(Employee employee) {
        workloadRows.clear();
        if (employee == null) {
            updateProjectedLoadState();
            return;
        }

        ObservableList<Assignment> assignments = resourceService.getAssignmentsByEmployee(employee);
        for (Assignment assignment : assignments) {
            Project project = findProjectForAssignment(assignment);
            if (project == null || project.getStatus() != Status.ACTIVE) {
                continue;
            }
            workloadRows.add(new WorkloadRow(
                    project.getTitle(),
                    assignment.getRole(),
                    assignment,
                    assignment.getAllocatedHours()
            ));
        }
        updateProjectedLoadState();
    }

    private Project findProjectForAssignment(Assignment target) {
        for (Project project : resourceService.findAllProjects()) {
            for (Assignment assignment : project.getAssignments()) {
                if (assignment == target) {
                    return project;
                }
            }
        }
        return null;
    }

    private void updateProjectedLoadState() {
        double existingHours = workloadRows.stream().mapToDouble(WorkloadRow::getHours).sum();
        Double newHours = parseHours(hoursField.getText());
        double additional = newHours == null ? 0.0 : newHours;
        double projectedTotal = existingHours + additional;

        projectedLoadLabel.setText(String.format("Projected Load: %.1f / 40", projectedTotal));
        boolean overload = projectedTotal > 40.0;
        projectedLoadLabel.setStyle(overload ? "-fx-text-fill: red;" : "-fx-text-fill: green;");

        boolean invalidForm = employeeBox.getValue() == null
                || roleField.getText() == null
                || roleField.getText().isBlank()
                || newHours == null
                || newHours <= 0;

        confirmButton.setDisable(overload || invalidForm || currentProject == null);
    }

    private Double parseHours(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }
        try {
            return Double.parseDouble(text.trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private void closeDialog() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    private void showError(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class WorkloadRow {
        private final StringProperty projectTitle;
        private final StringProperty role;
        private final Assignment assignment;
        private final DoubleProperty hours;

        public WorkloadRow(String projectTitle, String role, Assignment assignment, double hours) {
            this.projectTitle = new SimpleStringProperty(projectTitle);
            this.role = new SimpleStringProperty(role);
            this.assignment = assignment;
            this.hours = new SimpleDoubleProperty(hours);
        }

        public StringProperty projectTitleProperty() {
            return projectTitle;
        }

        public StringProperty roleProperty() {
            return role;
        }

        public Assignment getAssignment() {
            return assignment;
        }

        public double getHours() {
            return hours.get();
        }

        public void setHours(double hours) {
            this.hours.set(hours);
        }

        public DoubleProperty hoursProperty() {
            return hours;
        }
    }

    private static class FocusCommitDoubleCell extends TextFieldTableCell<WorkloadRow, Double> {
        private final DoubleStringConverter converter = new DoubleStringConverter();
        private boolean listenerAttached;

        FocusCommitDoubleCell() {
            super(new DoubleStringConverter());
        }

        @Override
        public void startEdit() {
            super.startEdit();
            if (!isEditing()) {
                return;
            }

            TextField editor = (TextField) getGraphic();
            if (editor == null || listenerAttached) {
                return;
            }

            listenerAttached = true;
            editor.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
                if (!isFocused && isEditing()) {
                    try {
                        commitEdit(converter.fromString(editor.getText()));
                    } catch (RuntimeException ex) {
                        cancelEdit();
                    }
                }
            });
        }
    }
}
