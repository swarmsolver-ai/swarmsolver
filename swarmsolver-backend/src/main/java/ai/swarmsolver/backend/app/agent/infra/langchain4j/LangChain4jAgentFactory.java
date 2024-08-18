package ai.swarmsolver.backend.app.agent.infra.langchain4j;

import ai.swarmsolver.backend.app.agent.domain.*;
import ai.swarmsolver.backend.app.agent.infra.persistence.AgentPersistenceAccessImpl;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LangChain4jAgentFactory implements AgentFactory<LangChain4jAgent, LangChain4jAgentSpecification> {

    private final AgentConversationAccess conversationAccess;

    private final AgentRepository agentRepository;
    
    private final FileSystemMessageStore fileSystemMessageStore;

    public LangChain4jAgentFactory(AgentConversationAccess conversationAccess, AgentRepository agentRepository, FileSystemMessageStore fileSystemMessageStore) {
        this.conversationAccess = conversationAccess;
        this.agentRepository = agentRepository;
        this.fileSystemMessageStore = fileSystemMessageStore;
    }

    @Override
    public Class<LangChain4jAgent> agentClass() {
        return LangChain4jAgent.class;
    }

    @Override
    public LangChain4jAgent createAgent(LangChain4jAgentSpecification agentSpecification, AgentState agentState) {

        List<Object> tools = new ArrayList<>();
        tools.add(new UserProxy());
        if (agentSpecification.getToolSpecifications() != null) {
            tools.addAll(agentSpecification.getToolSpecifications());
        }

        AgentCoordinate agentCoordinate = agentState.getAgentCoordinate();
        MessageWindowChatMemory chatMemory = new MessageWindowChatMemory.Builder()
            .chatMemoryStore(fileSystemMessageStore)
            .id(agentCoordinate)
            .maxMessages(100)
            .build();
        ChatLanguageModel languageModel = agentSpecification.getChatModelSupplier().create();
        AgentPersistenceAccessImpl persistenceAccess = new AgentPersistenceAccessImpl(agentCoordinate, agentRepository);

        return LangChain4jAgent.builder()
                .languageModel(languageModel)
                .chatMemory(chatMemory)
                .systemMessage(agentSpecification.getSystemMessage())
                .tools(tools)
                .agentState(agentState)
                .conversationAccess(conversationAccess)
                .persistenceAccess(persistenceAccess)
                .build();
    }

}
