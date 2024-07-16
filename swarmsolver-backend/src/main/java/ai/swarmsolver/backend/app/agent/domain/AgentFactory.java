package ai.swarmsolver.backend.app.agent.domain;

public interface AgentFactory<AGENT_CLASS extends Agent, AGENT_SPEC extends AgentSpecification<AGENT_CLASS>> {
    Class<AGENT_CLASS> agentClass();
    AGENT_CLASS createAgent(AGENT_SPEC agentSpecification, AgentState memento);
}

