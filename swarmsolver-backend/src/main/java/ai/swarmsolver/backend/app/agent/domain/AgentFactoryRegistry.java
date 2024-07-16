package ai.swarmsolver.backend.app.agent.domain;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AgentFactoryRegistry {
    
    private final Map<Class<? extends Agent>, AgentFactory<? extends Agent, ? extends AgentSpecification<? extends Agent>>> agentFactoryByClass = new HashMap<>();
    
    public AgentFactoryRegistry(List<AgentFactory<? extends Agent, ? extends AgentSpecification<? extends Agent>>> agentFactories) {
        agentFactories.forEach(factory -> agentFactoryByClass.put(factory.agentClass(), factory));
    }

    public <AGENT_CLASS extends Agent, AGENT_SPECIFICATION extends AgentSpecification<AGENT_CLASS>> AgentFactory<AGENT_CLASS, AGENT_SPECIFICATION> getFactory(Class<AGENT_CLASS> agentClass) {
        @SuppressWarnings("unchecked")
        AgentFactory<AGENT_CLASS, AGENT_SPECIFICATION> factory = (AgentFactory<AGENT_CLASS, AGENT_SPECIFICATION>) agentFactoryByClass.get(agentClass);
        return factory;
    }}
