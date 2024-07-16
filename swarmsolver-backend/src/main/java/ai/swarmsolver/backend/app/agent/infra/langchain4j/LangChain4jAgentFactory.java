package ai.swarmsolver.backend.app.agent.infra.langchain4j;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import ai.swarmsolver.backend.app.agent.domain.*;
import ai.swarmsolver.backend.app.agent.infra.persistence.AgentPersistenceAccessImpl;
import ai.swarmsolver.backend.app.tool.service.ToolService;
import org.springframework.stereotype.Component;

@Component
public class LangChain4jAgentFactory implements AgentFactory<LangChain4jAgent, LangChain4jAgentSpecification> {

    private final ToolService toolService;

    private final AgentConversationAccess conversationAccess;

    private final AgentRepository agentRepository;
    
    private final FileSystemMessageStore fileSystemMessageStore;

    public LangChain4jAgentFactory(ToolService toolService, AgentConversationAccess conversationAccess, AgentRepository agentRepository, FileSystemMessageStore fileSystemMessageStore) {
        this.toolService = toolService;
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
                .agentState(agentState)
                .conversationAccess(conversationAccess)
                .persistenceAccess(persistenceAccess)
                .build();
    }

}
