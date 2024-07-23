package ai.swarmsolver.backend.app.agent.domain;

import ai.swarmsolver.backend.infra.DirectoryStructure;
import ai.swarmsolver.backend.infra.scripting.ScriptingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScriptBasedAgentSpecificationRegistry implements AgentSpecificationRegistry {

    private final DirectoryStructure directoryStructure;

    private final ScriptingService scriptingService;

    public ScriptBasedAgentSpecificationRegistry(DirectoryStructure directoryStructure, DirectoryStructure directoryStructure1, ScriptingService scriptingService) {
        this.directoryStructure = directoryStructure1;
        this.scriptingService = scriptingService;
    }

    @Override
    public AgentSpecification<? extends Agent> getSpecification(String workspaceName, String agentName) {
        AgentConfigWorkspaceStructure workspaceStructure = new AgentConfigWorkspaceStructure(workspaceName, directoryStructure);
        return (AgentSpecification<? extends Agent> ) scriptingService.runFile(workspaceName, workspaceStructure.getAgentsConfigFile());
    }

}
