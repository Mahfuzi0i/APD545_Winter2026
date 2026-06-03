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

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javax.inject.Inject;
import nexus.di.AppInjector;
import nexus.model.Assignment;
import nexus.model.OverAllocationException;
import nexus.model.Project;
import nexus.service.ProjectService;
import nexus.service.ResourceService;

import java.io.IOException;

public class DashboardController {
    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> statusFilter;

    @FXML
    private ListView<Project> projectList;

    @FXML
    private Label projectTitle;

    @FXML
    private Label projectType;

    @FXML
    private Label totalCostLabel;

    @FXML
    private TableView<Assignment> assignmentTable;

    @FXML
    private TableColumn<Assignment, String> employeeCol;

    @FXML
    private TableColumn<Assignment, String> roleCol;

    @FXML
    private TableColumn<Assignment, Double> hoursCol;

    @FXML
    private TableColumn<Assignment, Double> costCol;

    @FXML
    private Button addTeamMemberButton;

    @Inject
    private ProjectService projectService;

    @Inject
    private ResourceService resourceService;
    private FilteredList<Project> filteredProjects;
    private StringExpression totalCostBinding;
    private final javafx.collections.ObservableList<Assignment> emptyAssignments = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        filteredProjects = new FilteredList<>(projectService.findAllProjects(), project -> true);
        projectList.setItems(filteredProjects);
        assignmentTable.setItems(emptyAssignments);

        projectList.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Project item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getTitle() + " (" + item.getStatus() + ")");
            }
        });

        statusFilter.getItems().addAll("ACTIVE", "CLOSED");
        statusFilter.getSelectionModel().select("ACTIVE");

        searchField.textProperty().addListener((obs, oldText, newText) -> applyFilter());
        statusFilter.valueProperty().addListener((obs, oldValue, newValue) -> applyFilter());
        projectList.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldProject, newProject) -> showProject(newProject));

        assignmentTable.setEditable(true);
        employeeCol.setCellValueFactory(cell ->
                new ReadOnlyStringWrapper(cell.getValue().getEmployee().getName()));
        roleCol.setCellValueFactory(cell -> cell.getValue().roleProperty());
        hoursCol.setCellValueFactory(cell -> cell.getValue().allocatedHoursProperty().asObject());
        hoursCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        hoursCol.setOnEditCommit(event -> {
            Assignment assignment = event.getRowValue();
            double oldValue = assignment.getAllocatedHours();
            double newValue = event.getNewValue();
            try {
                resourceService.updateAssignmentHours(assignment, newValue);
            } catch (OverAllocationException | IllegalArgumentException ex) {
                assignment.setAllocatedHours(oldValue);
                showError("Invalid hours update", ex.getMessage());
            }
            assignmentTable.refresh();
        });

        costCol.setCellValueFactory(cell -> cell.getValue().getCost().asObject());
        costCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", item));
                }
            }
        });

        addTeamMemberButton.setOnAction(event -> handleAddTeamMember());
    }

    private void applyFilter() {
        String titleFilter = searchField.getText() == null ? "" : searchField.getText().trim().toLowerCase();
        String selectedStatus = statusFilter.getValue();

        filteredProjects.setPredicate(project -> {
            boolean titleMatches = titleFilter.isBlank()
                    || project.getTitle().toLowerCase().contains(titleFilter);
            boolean statusMatches = selectedStatus == null
                    || project.getStatus().name().equalsIgnoreCase(selectedStatus);
            return titleMatches && statusMatches;
        });
    }

    private void showProject(Project project) {
        if (project == null) {
            projectTitle.setText("");
            projectType.setText("");
            assignmentTable.setItems(emptyAssignments);
            unbindTotalCost();
            totalCostLabel.setText("Total Cost: $0.00");
            return;
        }

        projectTitle.setText(project.getTitle());
        projectType.setText(project.getClass().getSimpleName());
        assignmentTable.setItems(project.getAssignments());
        bindTotalCost(project);
    }

    private void bindTotalCost(Project project) {
        unbindTotalCost();
        DoubleBinding totalCost = Bindings.createDoubleBinding(
                () -> project.getAssignments().stream().mapToDouble(a -> a.getCost().get()).sum(),
                project.getAssignments()
        );
        totalCostBinding = Bindings.format("Total Cost: $%.2f", totalCost);
        totalCostLabel.textProperty().bind(totalCostBinding);
    }

    private void unbindTotalCost() {
        if (totalCostBinding != null) {
            totalCostLabel.textProperty().unbind();
            totalCostBinding = null;
        }
    }

    @FXML
    private void handleAddTeamMember() {
        Project selectedProject = projectList.getSelectionModel().getSelectedItem();
        if (selectedProject == null) {
            showError("No project selected", "Please select a project before adding a team member.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/allocator.fxml"));
            loader.setController(AppInjector.controllerFactory().call(AllocatorController.class));
            loader.setControllerFactory(AppInjector.controllerFactory());
            Scene scene = new Scene(loader.load());

            AllocatorController allocatorController = loader.getController();
            allocatorController.setProject(selectedProject);

            Stage dialog = new Stage();
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(projectList.getScene().getWindow());
            dialog.setTitle("Resource Allocator");
            dialog.setScene(scene);
            dialog.showAndWait();

            assignmentTable.refresh();
            bindTotalCost(selectedProject);
        } catch (IOException e) {
            showError("Unable to open allocator", e.getMessage());
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
