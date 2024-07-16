package ai.swarmsolver.backend.app.agent.domain;

public interface AgentPersistenceAccess {
    void persist(AgentState memento);
}
