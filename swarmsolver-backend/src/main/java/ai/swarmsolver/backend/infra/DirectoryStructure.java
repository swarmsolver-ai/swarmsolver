package ai.swarmsolver.backend.infra;

import ai.swarmsolver.backend.app.ApplicationProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

@Component
public class DirectoryStructure {

    private final ApplicationProperties applicationProperties;

    public DirectoryStructure(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public String getWorkSpaceDir(String workspaceName) {
        return applicationProperties.getWorkspaces().get(workspaceName);
    }

    public String getConfigDir(String workspaceName) {
        return getWorkSpaceDir(workspaceName) + File.separator + "config";
    }

    public String getDataDir(String workspaceName) {
        return getWorkSpaceDir(workspaceName) + File.separator + "data";
    }

    public Map<String, String> getWorkSpaces() {
        return applicationProperties.getWorkspaces();
    }

}
