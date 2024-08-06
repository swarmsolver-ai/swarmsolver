package ai.swarmsolver.backend.app.agent.app;

import ai.swarmsolver.backend.app.agent.domain.*;
import ai.swarmsolver.backend.app.conversation.ConversationCoordinate;
import ai.swarmsolver.backend.app.conversation.ConversationService;
import ai.swarmsolver.backend.app.task.model.TaskCoordinate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AgentService {

    private final AgentSpecificationRegistry agentSpecificationRegistry;

    private final AgentFactoryRegistry agentFactoryRegistry;

    private final AgentRepository agentRepository;

    private final ConversationService conversationService;

    public AgentService(AgentSpecificationRegistry agentSpecificationRegistry, AgentFactoryRegistry agentFactoryRegistry, AgentRepository agentRepository, ConversationService conversationService) {
        this.agentFactoryRegistry = agentFactoryRegistry;
        this.agentRepository = agentRepository;
        this.agentSpecificationRegistry = agentSpecificationRegistry;
        this.conversationService = conversationService;
    }

    public Agent getAgent(AgentCoordinate agentCoordinate) {
        Agent agent =  agentRepository.loadFromCache(agentCoordinate);
        if (agent == null) {
            AgentState agentState = agentRepository.readState(agentCoordinate);
            agent = createAgent(agentState);
        }
        return agent;
    }

    public Agent createAgent(String agentName, TaskCoordinate taskCoordinate) {
        AgentId newAgentId = AgentId.of("agent-"+UUID.randomUUID());
        AgentCoordinate agentCoordinate = AgentCoordinate.of(taskCoordinate, newAgentId);
        ConversationCoordinate conversationCoordinate = conversationService.initConversation(taskCoordinate);
        AgentState agentState = AgentState.builder()
                .agentName(agentName)
                .agentCoordinate(agentCoordinate)
                .conversationCoordinate(conversationCoordinate)
                .build();
        agentRepository.writeState(agentCoordinate, agentState);
        return createAgent(agentState);
    }

    private <AC extends Agent, AS extends AgentSpecification<AC>> AC  createAgent(AgentState agentState) {
        @SuppressWarnings("unchecked") AS agentSpecification = (AS) agentSpecificationRegistry.getSpecification(agentState.getAgentCoordinate().getTaskCoordinate().getWorkSpaceName(), agentState.getAgentName());
        AgentFactory<AC, AS> agentFactory = agentFactoryRegistry.getFactory(agentSpecification.agentClass());
        AC agent = agentFactory.createAgent(agentSpecification, agentState);
        agentRepository.saveToCache(agent);
        return agent;
    }

    public List<AgentDescriptorDTO> list(String workspaceName) {
        return agentSpecificationRegistry.getSpecifications(workspaceName).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private AgentDescriptorDTO toDTO(AgentSpecification<? extends Agent> agentSpecification) {
        return AgentDescriptorDTO.builder()
                .name(agentSpecification.getAgentName())
                .build();
    }
}
