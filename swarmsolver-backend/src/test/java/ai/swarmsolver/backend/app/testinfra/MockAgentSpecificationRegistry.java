package ai.swarmsolver.backend.app.testinfra;

import ai.swarmsolver.backend.app.agent.domain.Agent;
import ai.swarmsolver.backend.app.agent.domain.AgentSpecification;
import ai.swarmsolver.backend.app.agent.domain.AgentSpecificationRegistry;

import java.util.ArrayList;
import java.util.List;

public class MockAgentSpecificationRegistry implements AgentSpecificationRegistry {

    private final List<AgentSpecification<? extends Agent>> agentSpecs = new ArrayList<>();

    public MockAgentSpecificationRegistry() {
        initSpecs();
    }

    private void initSpecs() {
        agentSpecs.add(new MockAgentSpec("mock-agent"));
    }

    @Override
    public AgentSpecification getSpecification(String workSpaceName, String description) {
        return agentSpecs.get(0);
    }

    @Override
    public List<AgentSpecification<? extends Agent>> getSpecifications(String workspaceName) {
        return agentSpecs;
    }
}
