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

import javafx.collections.ObservableList;
import javax.inject.Inject;
import nexus.model.Project;
import nexus.repository.ProjectRepository;

public class ProjectService {
    @Inject
    private ProjectRepository projectRepository;

    public ProjectService() {}

    public ObservableList<Project> findAllProjects() {
        return projectRepository.findAll();
    }
}
