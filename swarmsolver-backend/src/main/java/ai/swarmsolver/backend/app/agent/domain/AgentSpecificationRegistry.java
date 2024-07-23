package ai.swarmsolver.backend.app.agent.domain;

public interface AgentSpecificationRegistry {
    AgentSpecification getSpecification(String workSpaceName, String description);
}

