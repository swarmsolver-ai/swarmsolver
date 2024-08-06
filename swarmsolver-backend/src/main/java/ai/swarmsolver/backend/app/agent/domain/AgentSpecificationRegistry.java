package ai.swarmsolver.backend.app.agent.domain;

import java.util.List;

public interface AgentSpecificationRegistry {
    AgentSpecification getSpecification(String workSpaceName, String description);
    List<AgentSpecification<? extends Agent>> getSpecifications(String workspaceName);
}

