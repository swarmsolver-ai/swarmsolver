package ai.swarmsolver.backend.app.testinfra;

import ai.swarmsolver.backend.app.agent.domain.AgentSpecification;
import ai.swarmsolver.backend.app.agent.domain.AgentSpecificationRegistry;

public class MockAgentSpecificationRegistry implements AgentSpecificationRegistry {

    @Override
    public AgentSpecification getSpecification(String workSpaceName, String description) {
        return new MockAgentSpec();
    }
}
