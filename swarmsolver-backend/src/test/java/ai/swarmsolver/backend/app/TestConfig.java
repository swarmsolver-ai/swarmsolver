package ai.swarmsolver.backend.app;

import ai.swarmsolver.backend.app.agent.domain.AgentFactory;
import ai.swarmsolver.backend.app.agent.domain.AgentRepository;
import ai.swarmsolver.backend.app.agent.domain.AgentSpecificationRegistry;
import ai.swarmsolver.backend.app.agent.infra.conversation.AgentConversationAccessImpl;
import ai.swarmsolver.backend.app.testinfra.MockAgent;
import ai.swarmsolver.backend.app.testinfra.MockAgentFactory;
import ai.swarmsolver.backend.app.testinfra.MockAgentSpec;
import ai.swarmsolver.backend.app.testinfra.MockAgentSpecificationRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "ai.swarmsolver.backend.app",
        "ai.swarmsolver.backend.infra",
})
public class TestConfig {

    @Bean
    ApplicationProperties applicationProperties() {
        return new ApplicationProperties();
    }

    @Autowired
    private AgentConversationAccessImpl logAccess;

    @Autowired
    private AgentRepository agentRepository;

    @Bean
    AgentFactory<MockAgent, MockAgentSpec> mockAgentFactory() {
        return new MockAgentFactory(logAccess, agentRepository);
    }

    @Bean
    AgentSpecificationRegistry agentSpecificationRegistry() {
        return new MockAgentSpecificationRegistry();
    }

}
