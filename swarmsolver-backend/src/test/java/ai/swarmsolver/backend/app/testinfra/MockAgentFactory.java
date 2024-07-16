package ai.swarmsolver.backend.app.testinfra;

import ai.swarmsolver.backend.app.agent.domain.*;
import ai.swarmsolver.backend.app.agent.infra.conversation.AgentConversationAccessImpl;
import ai.swarmsolver.backend.app.agent.infra.persistence.AgentPersistenceAccessImpl;

public class MockAgentFactory implements AgentFactory<MockAgent, MockAgentSpec> {

    private final AgentConversationAccessImpl agentConversationAccessImpl;

    private final AgentRepository agentRepository;

    public MockAgentFactory(AgentConversationAccessImpl agentConversationAccessImpl, AgentRepository agentRepository) {
        this.agentConversationAccessImpl = agentConversationAccessImpl;
        this.agentRepository = agentRepository;
    }

    @Override
    public Class<MockAgent> agentClass() {
        return MockAgent.class;
    }

    @Override
    public MockAgent createAgent(MockAgentSpec agentSpecification, AgentState agentState) {
        AgentCoordinate agentCoordinate = agentState.getAgentCoordinate();
        return MockAgent.builder()
                .agentState(agentState)
                .conversationAccess(agentConversationAccessImpl)
                .persistenceAccess(new AgentPersistenceAccessImpl(agentCoordinate, agentRepository))
                .build();
    }


}
