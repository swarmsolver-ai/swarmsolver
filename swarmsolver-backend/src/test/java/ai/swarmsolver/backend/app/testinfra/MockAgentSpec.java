package ai.swarmsolver.backend.app.testinfra;

import ai.swarmsolver.backend.app.agent.domain.AgentSpecification;

public class MockAgentSpec implements AgentSpecification<MockAgent> {

    private String agentName;

    public MockAgentSpec(String agentName) {
        this.agentName = agentName;
    }

    @Override
    public String getAgentName() {
        return agentName;
    }

    @Override
    public Class<MockAgent> agentClass() {
        return MockAgent.class;
    }
}
