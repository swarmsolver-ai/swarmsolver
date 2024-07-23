package ai.swarmsolver.backend.app.agent.domain;

import ai.swarmsolver.backend.infra.DirectoryStructure;

import java.io.File;

public class AgentConfigWorkspaceStructure {

    private final String workSpaceName;

    private final DirectoryStructure directoryStructure;

    public AgentConfigWorkspaceStructure(String workSpaceName, DirectoryStructure directoryStructure) {
        this.workSpaceName = workSpaceName;
        this.directoryStructure = directoryStructure;
    }


    public String getAgentsConfigFile() {
        return directoryStructure.getConfigDir(workSpaceName) + File.separator + "agents.groovy";
    }
}
