package ai.swarmsolver.backend.app.agent.domain;

import ai.swarmsolver.backend.infra.DirectoryStructure;
import ai.swarmsolver.backend.infra.scripting.ScriptingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScriptBasedAgentSpecificationRegistry implements AgentSpecificationRegistry {

    private final AgentConfigWorkspaceStructure workspaceStructure;

    private final ScriptingService scriptingService;

    public ScriptBasedAgentSpecificationRegistry(DirectoryStructure directoryStructure, ScriptingService scriptingService) {
        this.workspaceStructure = new AgentConfigWorkspaceStructure(directoryStructure);
        this.scriptingService = scriptingService;
    }

    @Override
    public AgentSpecification<? extends Agent> getSpecification(String agentName) {
       return (AgentSpecification<? extends Agent> ) scriptingService.runFile(workspaceStructure.getAgentsConfigFile());
    }

}
