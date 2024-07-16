package ai.swarmsolver.backend.app.agent.domain;

import ai.swarmsolver.backend.infra.DirectoryStructure;

import java.io.File;

public class AgentConfigWorkspaceStructure {

    private final DirectoryStructure directoryStructure;

    public AgentConfigWorkspaceStructure(DirectoryStructure directoryStructure) {
        this.directoryStructure = directoryStructure;
    }


    public String getAgentsConfigFile() {
        return directoryStructure.getConfigDir() + File.separator + "agents.groovy";
    }
}
