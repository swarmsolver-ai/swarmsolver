package ai.swarmsolver.backend.app.agent.domain;

import ai.swarmsolver.backend.infra.DirectoryStructure;
import ai.swarmsolver.backend.infra.scripting.ScriptingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class ScriptBasedAgentSpecificationRegistry implements AgentSpecificationRegistry {

    private final DirectoryStructure directoryStructure;

    private final ScriptingService scriptingService;

    public ScriptBasedAgentSpecificationRegistry(DirectoryStructure directoryStructure, ScriptingService scriptingService) {
        this.directoryStructure = directoryStructure;
        this.scriptingService = scriptingService;
    }

    @Override
    public AgentSpecification<? extends Agent> getSpecification(String workspaceName, String agentName) {
        List<AgentSpecification<? extends Agent>> agentSpecifications = getSpecifications(workspaceName);
        return agentSpecifications.stream()
                .filter(agentSpecification -> agentName.equals(agentSpecification.getAgentName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("no agent description found for agent name %s ", agentName)));
    }

    public List<AgentSpecification<? extends Agent>> getSpecifications(String workspaceName) {
        AgentConfigWorkspaceStructure workspaceStructure = new AgentConfigWorkspaceStructure(workspaceName, directoryStructure);
        return (List<AgentSpecification<? extends Agent>>) scriptingService.runFile(workspaceName, workspaceStructure.getAgentsConfigFile());
    }

}
