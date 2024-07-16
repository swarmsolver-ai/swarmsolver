package ai.swarmsolver.backend.app.agent.domain;

public interface AgentSpecification<AC extends Agent> {
    public Class<AC> agentClass();
}

