package ai.swarmsolver.backend.infra;

import ai.swarmsolver.backend.app.ApplicationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DirectoryStructure {

    private final ApplicationProperties applicationProperties;

    public DirectoryStructure(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public String getMainDir() {
        return applicationProperties.getDataDir();
    }

    public String getConversationDir() {
        return getMainDir() + File.separator + "conversations";
    }

    public String getConfigDir() {
        return getMainDir() + File.separator + "config";
    }

    public String getProjectsConfigFile() {
        return getConfigDir() + File.separator + "projects.json";
    }

    public String getWorkspaceDir() {
        return getMainDir() + File.separator + "workspace";
    }

}
