package ai.swarmsolver.backend.app.testinfra;

import ai.swarmsolver.backend.app.agent.domain.AgentSpecification;

public class MockAgentSpec implements AgentSpecification<MockAgent> {

    @Override
    public Class<MockAgent> agentClass() {
        return MockAgent.class;
    }
}
