package ai.swarmsolver.backend.app.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import ai.swarmsolver.backend.infra.DirectoryStructure;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProjectService {

    private final DirectoryStructure directoryStructure;

    private final Map<String, Project> projectsByName = new HashMap<>();

    public ProjectService(DirectoryStructure directoryStructure) {
        this.directoryStructure = directoryStructure;

    }

    @SneakyThrows
   //  @PostConstruct
    public void initializeProjects() {
        File configFile = new File(directoryStructure.getProjectsConfigFile());

        ObjectMapper objectMapper = new ObjectMapper();
        Project[] projects = objectMapper.readValue(configFile, Project[].class);

        for (Project project : projects) {
            register(project);
        }
    }

    public void register(Project project) {
        projectsByName.put(project.getName(), project);
    }

    public Project getProject(String projectName) {
        return projectsByName.get(projectName);
    }

    public Collection<Project> getAllProjects() {
        return projectsByName.values();
    }
}
